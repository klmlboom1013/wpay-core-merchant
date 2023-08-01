package com.wpay.core.merchant.trnsmpi.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.common.Functions;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.port.PortOutFactory;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.MpiBasicInfoUseCasePort;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.MpiBasicInfoUseCaseVersion;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.MpiBasicInfoExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.MpiBasicInfoPersistencePort;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiTrns;
import com.wpay.core.merchant.trnsmpi.domain.MpiBasicInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Objects;

@Log4j2
@UseCase
@RequiredArgsConstructor
class MpiBasicInfoService implements MpiBasicInfoUseCasePort {

    private final PortOutFactory portOutFactory;

    @Override public final MpiBasicInfoUseCaseVersion getVersionCode() { return MpiBasicInfoUseCaseVersion.v1; }

    @Override
    public BaseResponse searchMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns) {
        final String wtid = activityMpiTrns.getMpiTrnsId().getWtid();
        final String mid = activityMpiTrns.getMid();
        log.info("[{}][{}] Set ActivityMpiBasicInfo - [{}]", mid, wtid, activityMpiTrns);

        MpiBasicInfoMapper mpiBasicInfoMapper;
        try {
            mpiBasicInfoMapper = this.getPersistencePort().loadActivitiesRun(activityMpiTrns);
        } catch (EntityNotFoundException e) {
            log.info("[{}][{}] MPI 통신 이력이 조회 되지 않아 MPI 기준 정보 조회 연동을 진행 합니다.", mid, wtid);
            return null;
        }

        /* Client 로 전달 DTO 세팅 */
        final MpiBasicInfo mpiBasicInfo = MpiBasicInfo.builder()
                .wtid(mpiBasicInfoMapper.getWtid())
                .mid(mpiBasicInfoMapper.getMid())
                .message(mpiBasicInfoMapper.getMessage())
                .build();

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }

    @Override
    public BaseResponse sendMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns) {
        final String wtid = activityMpiTrns.getMpiTrnsId().getWtid();
        final String mid = activityMpiTrns.getMid();
        log.info("[{}][{}] Set ActivityMpiBasicInfo - [{}]", mid, wtid, activityMpiTrns);

        /* MPI 통신 기준 정보 조회 요청 */
        MpiBasicInfoMapper mpiBasicInfoMapper = null;
        try {
            mpiBasicInfoMapper = this.getExternalPort().sendMpiBasicInfoRun(activityMpiTrns);
        } catch (Exception e) {
            log.error("[{}][{}] MpiBasicInfoExternal Error: {} - {}", mid, wtid, e.getClass().getName(), e.getMessage());
            throw new CustomException(ErrorCode.HTTP_STATUS_500, "가맹점 기준정보 조회 MPI 연동 오류.", e, wtid, mid);
        } finally {
            /* MPI 통신 이력 저장 */
            if(Objects.nonNull(mpiBasicInfoMapper)){
                activityMpiTrns.setMpiTrnsId(ActivityMpiTrns.MpiTrnsId.builder()
                        .wtid(wtid)
                        .srlno(Functions.makeSrlno.apply(new Date()))
                        .build());
                activityMpiTrns.setActivitySendMpi(ActivityMpiTrns.ActivitySendMpi.builder()
                        .connUrl(mpiBasicInfoMapper.getUrl())
                        .rspsGrmConts(mpiBasicInfoMapper.getMessage())
                        .payRsltCd(mpiBasicInfoMapper.getSendMpiBasicInfoResult().getResultCode())
                        .build());

                this.getPersistencePort().recodeActivitiesRun(activityMpiTrns);
            }
        }

        /* MPI 통신 기준 정보 조회 결과 코드 및 상태 검증 */
        final String resultCode = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getMpiReceiveResult().getCode();
        final String midStatus = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getMpiReceiveMpiStatus().getCode();
        log.info("[{}][{}] MPI 통신 기준 정보 조회 결과 [resultCode:{}][midStatus:{}]", mid, wtid, resultCode, midStatus);

        if(MpiBasicInfoMapper.MpiReceiveResult.RETCODE_FAIL.equals(resultCode))
            throw new CustomException(ErrorCode.HTTP_STATUS_500, "MPI 통신 기준 정보 조회 결과 응답 코드 실패.", wtid, mid);
        if(MpiBasicInfoMapper.MpiReceiveMpiStatus.STATUS_FAIL.equals(midStatus))
            throw new CustomException(ErrorCode.HTTP_STATUS_500, "MPI 통신 기준 정보 조히 결과 MID 상태 오류.", wtid, mid);

        /* Client 로 전달 DTO 세팅 */
        final MpiBasicInfo mpiBasicInfo = MpiBasicInfo.builder()
                .wtid(mpiBasicInfoMapper.getWtid())
                .mid(mpiBasicInfoMapper.getMid())
                .message(mpiBasicInfoMapper.getMessage())
                .build();

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }


    private MpiBasicInfoPersistencePort getPersistencePort() {
        return (MpiBasicInfoPersistencePort) this.portOutFactory.getPersistencePort(
                MpiBasicInfoUseCaseVersion.v1.toString(),
                this.getJobCode().toString());
    }

    private MpiBasicInfoExternalPort getExternalPort() {
        return (MpiBasicInfoExternalPort) this.portOutFactory.getExternalPort(
                MpiBasicInfoUseCaseVersion.v1.toString(),
                this.getJobCode().toString());
    }
}
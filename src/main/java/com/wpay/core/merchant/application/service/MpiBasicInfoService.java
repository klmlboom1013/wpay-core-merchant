package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCasePort;
import com.wpay.core.merchant.application.port.out.external.MpiBasicInfoExternalFactory;
import com.wpay.core.merchant.application.port.out.persistence.MpiBasicInfoPersistenceFactory;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.domain.ActivityMpiTrns;
import com.wpay.core.merchant.global.annotation.UseCase;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.VersionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;

@Log4j2
@UseCase
@RequiredArgsConstructor
class MpiBasicInfoService implements MpiBasicInfoUseCasePort {

    private final MpiBasicInfoPersistenceFactory mpiBasicInfoPersistenceFactory;
    private final MpiBasicInfoExternalFactory mpiBasicInfoExternalFactory;

    @Override public final VersionCode getVersionCode() { return VersionCode.v1; }

    @Override
    public BaseResponse searchMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns) {
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiTrns);
        MpiBasicInfoMapper mpiBasicInfoMapper;
        try {
            mpiBasicInfoMapper = mpiBasicInfoPersistenceFactory
                    .getMpiBasicInfoPersistence(this.getVersionCode(), this.getJobCode())
                    .loadActivitiesRun(activityMpiTrns);
        } catch (EntityNotFoundException e) {
            log.debug("MpiTrns 에서 해당 WTID[{}] 로 MPI 통신 이력이 조회 되지 않습니다.", activityMpiTrns.getMpiTrnsId().getWtid());
            return null;
        }
        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(MpiBasicInfo.builder()
                        .wtid(mpiBasicInfoMapper.getWtid())
                        .mid(mpiBasicInfoMapper.getMid())
                        .message(mpiBasicInfoMapper.getMessage())
                        .build())
                .build();
    }

    @Override
    public BaseResponse sendMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns) {
        final String wtid = activityMpiTrns.getMpiTrnsId().getWtid();
        final String mid = activityMpiTrns.getMid();
        log.info("[{}][{}] Set ActivityMpiBasicInfo - [{}]", mid, wtid, activityMpiTrns);

        /* MPI 통신 기준 정보 조회 요청 */
        MpiBasicInfoMapper mpiBasicInfoMapper;
        try {
            mpiBasicInfoMapper = mpiBasicInfoExternalFactory
                    .getMpiBasicInfoExternal(this.getVersionCode(), this.getJobCode())
                    .sendMpiBasicInfoRun(activityMpiTrns);
        } catch (Exception e) {
            log.error("[{}][{}] MpiBasicInfoExternal Error: {} - {}", mid, wtid, e.getClass().getName(), e.getMessage());
            throw e;
        } finally {
            /* MPI 통신 이력 저장 */
            this.mpiBasicInfoPersistenceFactory.getMpiBasicInfoPersistence(this.getVersionCode(), this.getJobCode())
                    .recodeActivitiesRun(activityMpiTrns);
        }

        /* MPI 통신 기준 정보 조회 결과 코드 및 상태 검증 */
        final String resultCode = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getMpiReceiveResult().getCode();
        final String midStatus = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getMpiReceiveMpiStatus().getCode();
        log.info("[{}][{}] MPI 통신 기준 정보 조회 결과 [resultCode:{}][midStatus:{}]", mid, wtid, resultCode, midStatus);

        if(MpiBasicInfoMapper.MpiReceiveResult.RETCODE_FAIL.equals(resultCode)) throw new RuntimeException("MPI 통신 기준 정보 조회 결과 응답 코드 실패.");
        if(MpiBasicInfoMapper.MpiReceiveMpiStatus.STATUS_FAIL.equals(midStatus)) throw new RuntimeException("MPI 통신 기준 정보 조히 결과 MID 상태 오류.");

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
}

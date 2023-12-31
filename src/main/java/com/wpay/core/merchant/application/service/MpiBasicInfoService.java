package com.wpay.core.merchant.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.port.PortOutFactory;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCasePort;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCaseVersion;
import com.wpay.core.merchant.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.application.port.out.external.MpiBasicInfoExternalPort;
import com.wpay.core.merchant.application.port.out.persistence.MpiBasicInfoPersistencePort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.CompleteMpiBasicInfo;
import com.wpay.core.merchant.domain.RecodeMpiBasicInfoTrns;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Log4j2
@UseCase
@RequiredArgsConstructor
class MpiBasicInfoService implements MpiBasicInfoUseCasePort {

    private final PortOutFactory portOutFactory;

    @Override public final MpiBasicInfoUseCaseVersion getVersionCode() { return MpiBasicInfoUseCaseVersion.v1; }

    @Override
    public BaseResponse searchMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final String jnoffcId = activityMpiBasicInfo.getJnoffcId();
        log.info("[{}][{}] Set ActivityMpiBasicInfo - [{}]", jnoffcId, wtid, activityMpiBasicInfo);

        MpiBasicInfoMapper mpiBasicInfoMapper;
        try {
            mpiBasicInfoMapper = this.getPersistencePort().loadActivitiesRun(activityMpiBasicInfo);
        } catch (EntityNotFoundException e) {
            log.info("[{}][{}] MPI 통신 이력이 조회 되지 않아 MPI 기준 정보 조회 연동을 진행 합니다.", jnoffcId, wtid);
            return null;
        }

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(CompleteMpiBasicInfo.builder()
                        .wtid(mpiBasicInfoMapper.getWtid())
                        .jnoffcId(mpiBasicInfoMapper.getJnoffcId())
                        .message(mpiBasicInfoMapper.getMessage())
                        .build())
                .build();
    }

    @Override
    public BaseResponse sendMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final String jnoffcId = activityMpiBasicInfo.getJnoffcId();
        log.info("[{}][{}] Set ActivityMpiBasicInfo - [{}]", jnoffcId, wtid, activityMpiBasicInfo);

        /* MPI 연동 트랜잭션 이력 저장 도매인 생성 */
        final RecodeMpiBasicInfoTrns recodeMpiBasicInfoTrns = RecodeMpiBasicInfoTrns.builder()
                .activityMpiBasicInfo(activityMpiBasicInfo).build();

        /* MPI 통신 기준 정보 조회 요청 */
        MpiBasicInfoMapper mpiBasicInfoMapper = null;
        try {
            mpiBasicInfoMapper = this.getExternalPort().sendMpiBasicInfoRun(activityMpiBasicInfo);
        } catch (CustomException ex) {
            if(Objects.nonNull(ex.getData()) && (ex.getData() instanceof MpiBasicInfoMapper)) {
                mpiBasicInfoMapper = (MpiBasicInfoMapper) ex.getData();
            } else {
                mpiBasicInfoMapper = MpiBasicInfoMapper.builder().build();
                mpiBasicInfoMapper.setSendMpiBasicInfoResult(MpiBasicInfoMapper.SendMpiBasicInfoResult.builder()
                        .resultCode(String.valueOf(ex.getErrorCode().getStatus())).errorMsg(ex.getDbRecodeMessage()).build());
            }
            throw ex;
        } finally {
            /* MPI 연동 트랜잭션 이력 저장 도매인 저장 */
            if(Objects.nonNull(mpiBasicInfoMapper)){
                recodeMpiBasicInfoTrns.setResultMapper(mpiBasicInfoMapper);
                this.getPersistencePort().recodeActivitiesRun(recodeMpiBasicInfoTrns);
            }
        }

        /* MPI 통신 기준 정보 조회 결과 코드 및 상태 검증 */
        final String resultCode = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getResultCode();
        final String midStatus = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getMidStatus();
        log.info("[{}][{}] MPI 통신 기준 정보 조회 결과 [resultCode:{}][midStatus:{}]", jnoffcId, wtid, resultCode, midStatus);

        if (Boolean.FALSE.equals(MpiBasicInfoMapper.MPI_RSLT_CD_SUCCESS.equals(resultCode)
                && MpiBasicInfoMapper.MPI_STATUS_CD_ACTIVE.equals(midStatus))) {
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_501)
                    .message(mpiBasicInfoMapper.getSendMpiBasicInfoResult().getErrorMsg())
                    .jnoffcId(jnoffcId).wtid(wtid)
                    .build());
        }

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(CompleteMpiBasicInfo.builder()
                        .wtid(mpiBasicInfoMapper.getWtid())
                        .jnoffcId(mpiBasicInfoMapper.getJnoffcId())
                        .message(mpiBasicInfoMapper.getMessage())
                        .build())
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

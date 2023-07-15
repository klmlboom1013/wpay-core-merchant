package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.adapter.out.dto.MpiBasicInfoMapper;
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
public class MpiBasicInfoService implements MpiBasicInfoUseCasePort {

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
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiTrns);

        this.mpiBasicInfoPersistenceFactory.getMpiBasicInfoPersistence(this.getVersionCode(), this.getJobCode())
                .recodeActivitiesRun(activityMpiTrns);

        MpiBasicInfoMapper mpiBasicInfoMapper;
        try {
            mpiBasicInfoMapper = mpiBasicInfoExternalFactory
                    .getMpiBasicInfoExternal(this.getVersionCode(), this.getJobCode())
                    .sendMpiBasicInfoRun(activityMpiTrns);
        } catch (Exception e) {
            log.error("MpiBasicInfoExternal Error: {} - {}", e.getClass().getName(), e.getMessage());
            throw e;
        }

        final MpiBasicInfo mpiBasicInfo = MpiBasicInfo.builder()
                .wtid(mpiBasicInfoMapper.getWtid())
                .mid(mpiBasicInfoMapper.getMid())
                .message(mpiBasicInfoMapper.getMessage())
                .build();

        // MPI 기준 정보 조회 결과 코드
        final String resultCode = mpiBasicInfo.getSendMpiBasicInfoResult().getResultCode();
        // MPI 기준 정보 조회 결과 MID 상태 코드
        final String midStatus = mpiBasicInfo.getSendMpiBasicInfoResult().getMidStatusCd();

        /* MPI 기준 정보 조회 결과 코드가 0000 이 아니거나 MID 상태 코드가 00 이 아니면 오류 처리 */
        if(Boolean.FALSE.equals("0000".equals(resultCode) && "00".equals(midStatus))) {
            log.error("MPI 통신 기준 정보 조히 결과 검증 오류: [{}]", mpiBasicInfo.getSendMpiBasicInfoResult().toString());
            throw new RuntimeException("MPI 통신 기준 정보 조히 결과 검증 오류.[resultCode:"+resultCode+"][midStatus:"+midStatus+"]");
        }

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }
}

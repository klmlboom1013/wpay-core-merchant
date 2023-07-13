package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCase;
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

@Log4j2
@UseCase
@RequiredArgsConstructor
public class MpiBasicInfoService implements MpiBasicInfoUseCase {

    private final MpiBasicInfoPersistenceFactory mpiBasicInfoPersistenceFactory;
    private final MpiBasicInfoExternalFactory mpiBasicInfoExternalFactory;

    @Override public final VersionCode getVersionCode() { return VersionCode.v1; }

    @Override
    public BaseResponse searchMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns) {
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiTrns);
        final MpiBasicInfo mpiBasicInfo = mpiBasicInfoPersistenceFactory
                .getMpiBasicInfoPersistence(this.getVersionCode(), this.getJobCode())
                .loadActivitiesRun(activityMpiTrns);
        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }

    @Override
    public BaseResponse sendMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns) {
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiTrns);

        this.mpiBasicInfoPersistenceFactory.getMpiBasicInfoPersistence(this.getVersionCode(), this.getJobCode())
                .recodeActivitiesRun(activityMpiTrns);

        final MpiBasicInfo mpiBasicInfo = mpiBasicInfoExternalFactory
                .getMpiBasicInfoExternal(this.getVersionCode(), this.getJobCode())
                .sendMpiBasicInfo(activityMpiTrns);
        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }
}

package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCase;
import com.wpay.core.merchant.application.port.out.external.MpiBasicInfoExternalFactory;
import com.wpay.core.merchant.application.port.out.persistence.MpiBasicInfoPersistenceFactory;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
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
    public BaseResponse searchMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo) {
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiBasicInfo);
        final MpiBasicInfo mpiBasicInfo = mpiBasicInfoPersistenceFactory
                .getMpiBasicInfoPersistence(this.getVersionCode(), this.getJobCode())
                .loadActivitiesRun(activityMpiBasicInfo);
        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }

    @Override
    public BaseResponse sendMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo) {
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiBasicInfo);
        final MpiBasicInfo mpiBasicInfo = mpiBasicInfoExternalFactory
                .getMpiBasicInfoExternal(this.getVersionCode(), this.getJobCode())
                .sendMpiBasicInfo(activityMpiBasicInfo);
        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }
}

package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.application.port.in.MpiBasicInfoCommand;
import com.wpay.core.merchant.application.port.in.SendMpiBasicInfoUseCase;
import com.wpay.core.merchant.application.port.out.persistence.LoadMpiBasicInfoPort;
import com.wpay.core.merchant.application.port.out.persistence.RecodeMpiBasicInfoPort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.global.annotation.UseCase;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.ApiVersion;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class SendMpiBasicInfoService implements SendMpiBasicInfoUseCase {

    private final LoadMpiBasicInfoPort loadMpiBasicInfoPort;
    private final RecodeMpiBasicInfoPort recodeMpiBasicInfoPort;

    @Override
    public final ApiVersion getVersion() { return ApiVersion.v1; }

    @Override
    public BaseResponse searchMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo) {
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiBasicInfo);
        final MpiBasicInfo mpiBasicInfo = this.loadMpiBasicInfoPort.loadActivitiesRun(activityMpiBasicInfo);
        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }
}

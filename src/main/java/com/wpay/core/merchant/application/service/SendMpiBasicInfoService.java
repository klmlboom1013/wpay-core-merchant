package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.adapter.in.dto.MerchantInfo;
import com.wpay.core.merchant.application.port.in.SendMpiBasicInfoUseCase;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.annotation.UseCase;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.ApiVersion;
import com.wpay.core.merchant.global.enums.JobCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class SendMpiBasicInfoService implements SendMpiBasicInfoUseCase {

    @Override public ApiVersion getVersion() { return ApiVersion.v1; }
    @Override public final JobCode getApiType() { return JobCode.SendMpiBasicInfo; }
    @Override public final BaseResponse execute(Object dto) { return this.sendMpiBasicInfo((MerchantInfo) dto); }

    @Override
    public BaseResponse sendMpiBasicInfo(MerchantInfo merchantInfo) {

        ActivityMpiBasicInfo mpiBasicInfo = ActivityMpiBasicInfo.builder()
                .mpiTrnsId(ActivityMpiBasicInfo.MpiTrnsId.builder().wtid(merchantInfo.getWtid()).build())
                .build();

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo)
                .build();
    }
}

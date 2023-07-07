package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.adapter.in.dto.MerchantInfo;
import com.wpay.core.merchant.application.port.in.SendMpiBasicInfoUseCase;
import com.wpay.core.merchant.global.annotation.UseCase;
import com.wpay.core.merchant.global.dto.BaseRestFulResponse;

@UseCase
public class SendMpiBasicInfoService implements SendMpiBasicInfoUseCase {

    @Override
    public BaseRestFulResponse sendMpiBasicInfo(MerchantInfo merchantInfo) {
        return null;
    }
}

package com.wpay.core.merchant.application.port.in;

import com.wpay.core.merchant.adapter.in.dto.MerchantInfo;
import com.wpay.core.merchant.global.dto.BaseRestFulResponse;

public interface SendMpiBasicInfoUseCase {
    BaseRestFulResponse sendMpiBasicInfo(MerchantInfo merchantInfo);
}

package com.wpay.core.merchant.application.port.in;

import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.dto.BaseResponse;

public interface SendMpiBasicInfoUseCase extends BasePortInUseCase {

    /** Business 구현 */
    BaseResponse sendMpiBasicInfo(ActivityMpiBasicInfo activityMpiBasicInfo);
}

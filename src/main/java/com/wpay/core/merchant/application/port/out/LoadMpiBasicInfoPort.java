package com.wpay.core.merchant.application.port.out;

import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfoMapper;

public interface LoadMpiBasicInfoPort {

    MpiBasicInfoMapper readActivities (ActivityMpiBasicInfo activityMpiBasicInfo);
}

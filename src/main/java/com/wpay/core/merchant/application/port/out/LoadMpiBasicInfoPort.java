package com.wpay.core.merchant.application.port.out;

import com.wpay.core.merchant.adapter.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.enums.JobCode;

public interface LoadMpiBasicInfoPort {

    MpiBasicInfoMapper readActivities (ActivityMpiBasicInfo activityMpiBasicInfo);
}

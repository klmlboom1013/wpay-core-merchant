package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfo;

public interface LoadMpiBasicInfoPort {

    MpiBasicInfo loadActivitiesRun (ActivityMpiBasicInfo activityMpiBasicInfo);
}

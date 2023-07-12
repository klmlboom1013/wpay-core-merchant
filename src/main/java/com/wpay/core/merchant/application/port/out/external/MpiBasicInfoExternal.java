package com.wpay.core.merchant.application.port.out.external;

import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.factory.port.out.ExternalPort;

public interface MpiBasicInfoExternal extends ExternalPort {
    @Override default JobCode getJobCode() { return JobCode.JOB_CODE_20; }

    MpiBasicInfo sendMpiBasicInfo(ActivityMpiBasicInfo activityMpiBasicInfo);
}

package com.wpay.core.merchant.application.port.out.external;

import com.wpay.core.merchant.adapter.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.domain.ActivityMpiTrns;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.factory.port.out.ExternalPort;

public interface MpiBasicInfoExternalPort extends ExternalPort {
    @Override default JobCode getJobCode() { return JobCode.JOB_CODE_20; }



    MpiBasicInfoMapper sendMpiBasicInfoRun(ActivityMpiTrns activityMpiTrns);
}

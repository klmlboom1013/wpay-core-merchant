package com.wpay.core.merchant.trnsmpi.application.port.out.external;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.ExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiBasicInfo;

public interface MpiBasicInfoExternalPort extends ExternalPort {

    @Override
    default JobCodes getJobCode(){
        return JobCodes.JOB_CODE_20;
    }

    MpiBasicInfoMapper sendMpiBasicInfoRun(ActivityMpiBasicInfo activityMpiBasicInfo);
}

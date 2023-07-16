package com.wpay.core.merchant.application.port.out.external;

import com.wpay.common.global.factory.port.out.ExternalPort;
import com.wpay.core.merchant.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.domain.ActivityMpiTrns;
import com.wpay.core.merchant.enums.MpiBasicInfoJobCode;

public interface MpiBasicInfoExternalPort extends ExternalPort {
    @Override default MpiBasicInfoJobCode getJobCode() { return MpiBasicInfoJobCode.JOB_CODE_20; }



    MpiBasicInfoMapper sendMpiBasicInfoRun(ActivityMpiTrns activityMpiTrns);
}

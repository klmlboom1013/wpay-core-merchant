package com.wpay.core.merchant.application.port.out.external;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.ExternalPort;
import com.wpay.core.merchant.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthVerifyExternalPort extends ExternalPort {
    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_19; }
    boolean sendVerifyAuthNumbRun(ActivityCellPhoneAuth activityCellPhoneAuth);
}

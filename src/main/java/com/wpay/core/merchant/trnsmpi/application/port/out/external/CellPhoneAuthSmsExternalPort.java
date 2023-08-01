package com.wpay.core.merchant.trnsmpi.application.port.out.external;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.ExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.CellPhoneAuthSmsMapper;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthSmsExternalPort extends ExternalPort {
    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_18; }
    CellPhoneAuthSmsMapper sendSmsAuthNumbRun(ActivityCellPhoneAuth activityCellPhoneAuth);
}

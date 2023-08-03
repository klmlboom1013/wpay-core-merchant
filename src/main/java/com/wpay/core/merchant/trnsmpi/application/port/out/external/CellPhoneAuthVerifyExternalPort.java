package com.wpay.core.merchant.trnsmpi.application.port.out.external;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.ExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.BaseCellPhoneAuthMapper;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthVerifyExternalPort extends ExternalPort {
    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_19; }
    BaseCellPhoneAuthMapper sendVerifyAuthNumbRun(ActivityCellPhoneAuth activityCellPhoneAuth);
}

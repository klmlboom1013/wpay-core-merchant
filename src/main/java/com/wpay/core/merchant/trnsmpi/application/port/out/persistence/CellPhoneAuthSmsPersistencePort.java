package com.wpay.core.merchant.trnsmpi.application.port.out.persistence;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.PersistencePort;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthSmsPersistencePort extends PersistencePort {
    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_18; }

    void saveTrnsSmsAuthNumbRun (ActivityCellPhoneAuth activityCellPhoneAuth);
}

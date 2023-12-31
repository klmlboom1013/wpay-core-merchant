package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.PersistencePort;
import com.wpay.core.merchant.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthVerifyPersistencePort extends PersistencePort {
    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_19; }

    void saveTrnsVerifyAuthNumbRun (ActivityCellPhoneAuth activityCellPhoneAuth);
}

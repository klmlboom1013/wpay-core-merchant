package com.wpay.core.merchant.trnsmpi.application.port.out.persistence;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.PersistencePort;
import com.wpay.core.merchant.trnsmpi.domain.RecodeCellPhoneAuthTrns;

public interface CellPhoneAuthSmsPersistencePort extends PersistencePort {
    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_18; }

    /**
     * SMS 인증번호 요청 횟수 제한 카운트 검증.
     */
    Integer countBySmsAuthNumb(String wtid);

    void saveTrnsSmsAuthNumbRun (RecodeCellPhoneAuthTrns recodeCellPhoneAuthTrns);
}

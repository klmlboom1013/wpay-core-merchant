package com.wpay.core.merchant.trnsmpi.application.port.out.external;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.ExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.BaseCellPhoneAuthMapper;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthSmsExternalPort extends ExternalPort {
    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_18; }

    /**
     * MVNO 사업자 확인
     */
    BaseCellPhoneAuthMapper sendConfirmMvnoCompanyRun(ActivityCellPhoneAuth activityCellPhoneAuth);

    /**
     * 인증번호 SMS 발송 요청
     */
    BaseCellPhoneAuthMapper sendSmsAuthNumbRun(ActivityCellPhoneAuth activityCellPhoneAuth);
}

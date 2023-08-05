package com.wpay.core.merchant.global.exception;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

public class LimitSendSmsException extends CustomException {

    private static final String defaultErrorMessage = "SMS 인증번호 요청 가능 횟수를 초과 하였습니다. 잠시 후 다시 진행 해 주세요.";

    public LimitSendSmsException(String wtid, String mid, final int limitCount, final int sendSmsCount) {
        super(ErrorCode.HTTP_STATUS_403, defaultErrorMessage + "[요청 제한 횟수: "+limitCount+"회][요청 횟수: "+sendSmsCount+"회]", wtid, mid);
    }
}

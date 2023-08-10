package com.wpay.core.merchant.global.exception;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.ErrorCode;

public class LimitSendSmsException extends CustomException {

    private static final String defaultErrorMessage = "SMS 인증번호 요청 가능 횟수를 초과 하였습니다. 잠시 후 다시 진행 해 주세요.";

    public LimitSendSmsException(String wtid, String jnoffcId, final int limitCount, final int sendSmsCount) {
        super(CustomExceptionData.builder()
                        .errorCode(ErrorCode.HTTP_STATUS_403).wtid(wtid).jnoffcId(jnoffcId)
                        .message(String.format("%s [요청 제한 횟수: %d 회][요청 횟수: %d 회]", defaultErrorMessage, limitCount, sendSmsCount))
                        .build());
    }
}

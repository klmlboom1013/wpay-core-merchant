package com.wpay.core.merchant.global.exception;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

public class JobCodeException extends CustomException {

    private static final String defaultErrorMessage = "유효 하지 않은 업무 구분 값 오류가 발생 했습니다.";

    public JobCodeException() {
        super(ErrorCode.HTTP_STATUS_500, defaultErrorMessage);
    }

    public JobCodeException(String wtid, String mid) {
        super(ErrorCode.HTTP_STATUS_500, defaultErrorMessage, wtid, mid);
    }

    public JobCodeException(ErrorCode errorCode, String message, Throwable e, String wtid, String mid) {
        super(errorCode, message, e, wtid, mid);
    }
}

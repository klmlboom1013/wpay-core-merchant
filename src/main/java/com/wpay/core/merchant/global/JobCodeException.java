package com.wpay.core.merchant.global;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

public class JobCodeException extends CustomException {

    public JobCodeException() {
        super(ErrorCode.HTTP_STATUS_500, "유효 하지 않은 업무 구분 값 오류 입니다.");
    }

    public JobCodeException(String wtid, String mid) {
        super(ErrorCode.HTTP_STATUS_500, "유효 하지 않은 업무 구분 값 오류 입니다.", wtid, mid);
    }

    public JobCodeException(ErrorCode errorCode, String message, Throwable e, String wtid, String mid) {
        super(errorCode, message, e, wtid, mid);
    }
}

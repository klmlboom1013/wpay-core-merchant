package com.wpay.core.merchant.global.exception;

import lombok.Getter;


@Getter
public class CustomException extends CommonException {

    public CustomException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public CustomException(ErrorCode errorCode, Throwable e, String wtid, String mid) {
        super(errorCode, e, wtid, mid);
    }

    public CustomException(ErrorCode errorCode, String message, Throwable e) {
        super(errorCode, message, e);
    }

    public CustomException(ErrorCode errorCode, Throwable e) {
        super(errorCode, e);
    }

    public CustomException(ErrorCode errorCode, String wtid, String mid) {
        super(errorCode, wtid, mid);
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CustomException(ErrorCode errorCode, String message, String wtid, String mid) {
        super(errorCode, message, wtid, mid);
    }
}

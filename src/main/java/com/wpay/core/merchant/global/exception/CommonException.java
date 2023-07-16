package com.wpay.core.merchant.global.exception;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;
    private String message;
    private Throwable e;

    private String wtid;
    private String mid;

    public CommonException(ErrorCode errorCode, String message){
        this.errorCode=errorCode;
        this.message=message;
    }

    public CommonException(ErrorCode errorCode, Throwable e, String wtid, String mid){
        this.errorCode=errorCode;
        this.message=errorCode.getMessage();
        this.e=e;
        this.wtid=wtid;
        this.mid=mid;
    }

    public CommonException(ErrorCode errorCode,String message, String wtid, String mid){
        this.errorCode=errorCode;
        this.wtid=wtid;
        this.mid=mid;
        this.message=message;
    }

    public CommonException(ErrorCode errorCode, String wtid, String mid){
        this.errorCode=errorCode;
        this.wtid=wtid;
        this.mid=mid;
    }

    public CommonException(ErrorCode errorCode, String message, Throwable e){
        this.errorCode=errorCode;
        this.message=message;
        this.e=e;
    }

    public CommonException(ErrorCode errorCode, Throwable e){
        this.errorCode=errorCode;
        this.message=errorCode.getMessage();
        this.e=e;
    }

    public CommonException(ErrorCode errorCode){
        this.errorCode=errorCode;
        this.message=errorCode.getMessage();
    }
}

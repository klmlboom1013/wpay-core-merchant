package com.wpay.core.merchant.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 값을 확인해 주세요."),

    //404 NOT_FOUND 잘못된 리소스 접근
    DISPLAY_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 리소스 접근 입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "처리 중 오류가 발생 했습니다. 잠시 후 다시 시도 해 주세요."),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원 하지 않는 Http Method 입니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 URI 정보 입니다.")
    ;

    private final HttpStatus status;
//    private final int status;
    private final String message;
}

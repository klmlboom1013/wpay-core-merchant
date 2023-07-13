package com.wpay.core.merchant.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@ToString
public class BaseResponse {
    private final String timestamp;
    private final Integer status;
    private final String message;
    private final Object data;

    @Builder
    private BaseResponse(@NonNull HttpStatus httpStatus, Object data){
        this.timestamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date());
        this.status=httpStatus.value();
        this.message=httpStatus.getReasonPhrase();
        this.data=data;
    }
}

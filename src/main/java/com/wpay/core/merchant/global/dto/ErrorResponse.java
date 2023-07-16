package com.wpay.core.merchant.global.dto;

import com.wpay.core.merchant.global.common.Functions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
@Getter
@ToString
@EqualsAndHashCode
public class ErrorResponse {
    String timestamp;
    Integer status;
    String error;
    String message;

    @Builder
    public ErrorResponse(HttpStatus httpStatus, String error, String message) {
        this.timestamp = Functions.getTimestampMilliSecond.apply(new Date());
        this.status = httpStatus.value();
        this.error = error;
        this.message = message;
    }
}

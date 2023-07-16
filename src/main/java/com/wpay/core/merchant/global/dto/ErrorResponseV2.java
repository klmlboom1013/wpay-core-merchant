package com.wpay.core.merchant.global.dto;

import com.wpay.core.merchant.global.common.Functions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
@Getter
@ToString
@EqualsAndHashCode
public class ErrorResponseV2 {
    String timestamp;
    Integer status;
    String error;
    String message;
    String wtid;
    String mid;

    @Builder
    public ErrorResponseV2(HttpStatus httpStatus, String error, String message, String wtid, String mid) {
        this.timestamp = Functions.getTimestampMilliSecond.apply(new Date());
        this.status = httpStatus.value();
        this.error = error;
        this.message = message;
        this.wtid=wtid;
        this.mid=mid;
    }
}

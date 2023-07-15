package com.wpay.core.merchant.global.dto;

import com.wpay.core.merchant.global.common.Functions;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.http.HttpStatus;

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
        this.timestamp = Functions.getTimestampMilliSecond.apply(new Date());
        this.status=httpStatus.value();
        this.message=httpStatus.getReasonPhrase();
        this.data=data;
    }
}

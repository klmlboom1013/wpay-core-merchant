package com.wpay.core.merchant.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@ToString
public class BaseRestFulResponse {
    private final String timestamp;
    private final Integer status;
    private final String message;
    private final Object data;

    @Builder
    private BaseRestFulResponse(@NonNull Integer status, @NonNull String message, @NonNull Object data){
        this.timestamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date());
        this.status=status;
        this.message=message;
        this.data=data;
    }

    public static BaseRestFulResponse createRestFulBaseResponse(@NonNull Integer status, @NonNull String message, @NonNull Object data) {
        return new BaseRestFulResponse(status, message, data);
    }
}

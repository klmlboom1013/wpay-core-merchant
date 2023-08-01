package com.wpay.core.merchant.trnsmpi.application.port.out.dto;


import lombok.Getter;

@Getter
public abstract class BaseCellPhoneAuthMapper {
    private final String wtid;
    private final String mid;
    private final String resultCode;
    private final String resultMsg;
    public BaseCellPhoneAuthMapper(String wtid, String mid, String resultCode, String resultMsg) {
        this.wtid = wtid;
        this.mid = mid;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}

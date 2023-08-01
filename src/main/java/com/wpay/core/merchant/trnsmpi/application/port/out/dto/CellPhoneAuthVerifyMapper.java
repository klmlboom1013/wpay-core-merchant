package com.wpay.core.merchant.trnsmpi.application.port.out.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CellPhoneAuthVerifyMapper extends BaseCellPhoneAuthMapper {

    @Builder
    public CellPhoneAuthVerifyMapper(String wtid, String mid, String resultCode, String resultMsg) {
        super(wtid, mid, resultCode, resultMsg);
    }
}

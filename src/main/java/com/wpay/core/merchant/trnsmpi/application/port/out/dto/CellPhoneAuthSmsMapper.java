package com.wpay.core.merchant.trnsmpi.application.port.out.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CellPhoneAuthSmsMapper extends BaseCellPhoneAuthMapper {

    @Builder
    public CellPhoneAuthSmsMapper(String wtid, String mid, String resultCode, String resultMsg) {
        super(wtid, mid, resultCode, resultMsg);
    }
}

package com.wpay.core.merchant.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MobileCarrier {
    SKT("61","N"),
    KTF("61","N"),
    LGT("61","N"),
    SKR("31","Y"),
    KTR("31","Y"),
    LGR("31","Y");

    private final String msgtype;
    private final String altteul;
}

package com.wpay.core.merchant.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MobiliansMsgType {
    CONFIRM_MVNO("31", "MVNO 사업자 확인"),
    SEND_SMS("61", "인증번호 SMS 밠송"),
    RESEND_SMS("65", "인증번호 SMS 재발송"),
    CERTIFICATION("63", "인증번호 인증 요청");

    private final String code;
    private final String description;
}

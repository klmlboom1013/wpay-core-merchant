package com.wpay.core.merchant.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MobileCarrier {
    SKT("N"),
    KTF("N"),
    LGT("N"),
    SKR("N"), /* SKR 알뜰폰 이지만 MVNO 사업자 번호 조회가 필요 하지않으므로 N으로 세팅 */
    KTR("Y"),
    LGR("Y");

    /**
     * <pre>
     *     mvno 사업자 번호 조회 필요 여부
     *     N: 메이저 통신사
     *     Y: 알뜰폰 통신사
     * </pre>
     */
    private final String altteul;

    public static MobileCarrier getInstance(String name) {
        return MobileCarrier.valueOf(name.toUpperCase());
    }
}

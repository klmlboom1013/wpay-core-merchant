package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.core.merchant.global.exception.CustomException;
import com.wpay.core.merchant.global.exception.ErrorCode;

/**
 * RestFul API URI @PathVariable {version} values
 */
public enum MpiBasicInfoVersion {
    v1;

    public boolean equals(MpiBasicInfoVersion code) {
        return this.toString().equals(code.toString());
    }

    public static MpiBasicInfoVersion getInstance(String version) {
        for(MpiBasicInfoVersion o : MpiBasicInfoVersion.values())
            if(o.toString().equals(version)) return o;
        throw new CustomException(ErrorCode.INVALID_PARAMETER, "URI Path parameter version: 지원 하지 않은 버전 입니다.");
    }
}

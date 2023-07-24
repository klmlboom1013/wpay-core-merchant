package com.wpay.core.merchant.global.enums;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

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
        throw new CustomException(ErrorCode.HTTP_STATUS_400, "URI Path parameter version: 지원 하지 않은 버전 입니다.");
    }
}

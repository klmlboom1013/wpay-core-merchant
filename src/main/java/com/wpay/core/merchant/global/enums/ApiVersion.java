package com.wpay.core.merchant.global.enums;

/**
 * RestFul API URI @PathVariable {version} values
 */
public enum ApiVersion {
    v1,v2,v3,v4,v5;

    public static ApiVersion getInstance(String version) {
        for(ApiVersion o : ApiVersion.values())
            if(o.toString().equals(version)) return o;
        throw new IllegalArgumentException("지원 하지 않은 version 입니다.");
    }
}

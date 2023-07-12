package com.wpay.core.merchant.global.enums;

/**
 * RestFul API URI @PathVariable {version} values
 */
public enum VersionCode {
    v1,v2,v3,v4,v5;

    public static VersionCode getInstance(String version) {
        for(VersionCode o : VersionCode.values())
            if(o.toString().equals(version)) return o;
        throw new IllegalArgumentException("지원 하지 않은 version 입니다.");
    }
}

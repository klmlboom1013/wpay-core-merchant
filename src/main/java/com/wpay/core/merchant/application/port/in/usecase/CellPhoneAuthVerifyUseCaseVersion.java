package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

/**
 * RestFul API URI @PathVariable {version} values
 */
public enum CellPhoneAuthVerifyUseCaseVersion {
    v1;

    public boolean equals(String code) {
        return this.toString().equals(code);
    }

    public static CellPhoneAuthVerifyUseCaseVersion getInstance(String version) {
        for(CellPhoneAuthVerifyUseCaseVersion o : CellPhoneAuthVerifyUseCaseVersion.values())
            if(o.equals(version)) return o;
        throw new CustomException(ErrorCode.HTTP_STATUS_400, "URI Path parameter version: 지원 하지 않은 버전 입니다.");
    }
}

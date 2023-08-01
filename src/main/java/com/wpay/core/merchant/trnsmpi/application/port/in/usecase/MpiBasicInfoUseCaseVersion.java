package com.wpay.core.merchant.trnsmpi.application.port.in.usecase;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

/**
 * RestFul API URI @PathVariable {version} values
 */
public enum MpiBasicInfoUseCaseVersion {
    v1;

    public static MpiBasicInfoUseCaseVersion getInstance(String version) {
        for(MpiBasicInfoUseCaseVersion o : MpiBasicInfoUseCaseVersion.values())
            if(o.toString().equals(version)) return o;
        throw new CustomException(ErrorCode.HTTP_STATUS_400, "URI Path parameter version: 지원 하지 않은 버전 입니다.");
    }
}

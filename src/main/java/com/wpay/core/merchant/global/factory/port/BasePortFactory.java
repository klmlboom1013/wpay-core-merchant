package com.wpay.core.merchant.global.factory.port;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoVersion;
import com.wpay.core.merchant.global.exception.CustomException;
import com.wpay.core.merchant.global.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

@Log4j2
public abstract class BasePortFactory {

    /**
     * Port Factory Mapper Key 생성: key = version.jobCode.portDvdCode
     */
    protected final String makeMapperKey (MpiBasicInfoVersion versionCode, JobCode jobCode, PortDvdCode portDvdCode){
        if(Objects.isNull(versionCode) || Objects.isNull(jobCode) || Objects.isNull(portDvdCode)) {
            log.error("Port Factory mapper Key 생성 데이터 검증 오류. -> versionCode: {} / jobCode: {} / portDvdCode: {}",versionCode, jobCode, portDvdCode);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        final String result = new StringBuilder()
                .append(versionCode).append(".")
                .append(jobCode).append(".")
                .append(portDvdCode).toString();
        return result.toUpperCase();
    }
}

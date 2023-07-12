package com.wpay.core.merchant.global.factory.port;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

@Log4j2
public abstract class BasePortFactory {

    protected final String makeMapperKey (VersionCode versionCode, JobCode jobCode, PortDvdCode portDvdCode){
        if(Objects.isNull(versionCode) || Objects.isNull(jobCode) || Objects.isNull(portDvdCode)) {
            log.warn(">> versionCode: {} / jobCode: {} / portDvdCode: {}",versionCode, jobCode, portDvdCode);
            return null;
        }

        final String result = new StringBuilder()
                .append(versionCode).append(".")
                .append(jobCode).append(".")
                .append(portDvdCode).toString();
        return result.toUpperCase();
    }
}

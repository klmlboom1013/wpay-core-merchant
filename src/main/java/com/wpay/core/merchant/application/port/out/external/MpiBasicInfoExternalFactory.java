package com.wpay.core.merchant.application.port.out.external;


import com.wpay.common.global.annotation.Factory;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.factory.port.BasePortFactory;
import com.wpay.common.global.factory.port.PortDvdCode;
import com.wpay.core.merchant.global.enums.MpiBasicInfoJobCode;
import com.wpay.core.merchant.global.enums.MpiBasicInfoVersion;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Factory
public final class MpiBasicInfoExternalFactory extends BasePortFactory {

    private final Map<String, MpiBasicInfoExternalPort> mpiBasicInfoExternalMapper = new HashMap<>();

    public MpiBasicInfoExternalFactory(List<MpiBasicInfoExternalPort> mpiBasicInfoExternalPorts) {
        if(CollectionUtils.isEmpty(mpiBasicInfoExternalPorts)){
            log.error("MpiBasicInfoExternal Interface 가 구현된 Persistence Adapter Bean 을 찾지 못 했습니다.");
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        for(MpiBasicInfoExternalPort external : mpiBasicInfoExternalPorts){
            final String key = this.makeMapperKey(
                    external.getVersionCode().toString(), external.getJobCode().toString(), external.getPortDvdCode().toString());
            log.debug(">> make MpiBasicInfoExternalMapper PUT key : {}", key);
            this.mpiBasicInfoExternalMapper.put(key, external);
        }
    }

    public MpiBasicInfoExternalPort getMpiBasicInfoExternal(MpiBasicInfoVersion versionCode, MpiBasicInfoJobCode mpiBasicInfoJobCode) {
        final String key = this.makeMapperKey(versionCode.toString(), mpiBasicInfoJobCode.toString(), PortDvdCode.external.toString());
        return Objects.requireNonNull(this.mpiBasicInfoExternalMapper.get(key), "mpiBasicInfoExternalMapper 에 저장된 ExternalAdapter Bean 이 없습니다.");
    }
}

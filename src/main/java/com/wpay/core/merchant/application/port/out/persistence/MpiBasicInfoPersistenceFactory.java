package com.wpay.core.merchant.application.port.out.persistence;

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
public final class MpiBasicInfoPersistenceFactory extends BasePortFactory {

    private final Map<String, MpiBasicInfoPersistencePort> mpiBasicInfoPersistenceMapper = new HashMap<>();

    public MpiBasicInfoPersistenceFactory(List<MpiBasicInfoPersistencePort> mpiBasicInfoPersistenceList) {
        if(CollectionUtils.isEmpty(mpiBasicInfoPersistenceList)){
            log.error("MpiBasicInfoPersistence Interface 가 구현된 Persistence Adapter Bean 을 찾지 못 했습니다.");
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        for(MpiBasicInfoPersistencePort persistence : mpiBasicInfoPersistenceList){
            final String key = this.makeMapperKey(
                    persistence.getVersionCode().toString(), persistence.getJobCode().toString(), persistence.getPortDvdCode().toString());
            this.mpiBasicInfoPersistenceMapper.put(key, persistence);
        }
    }

    public MpiBasicInfoPersistencePort getMpiBasicInfoPersistence(MpiBasicInfoVersion versionCode, MpiBasicInfoJobCode mpiBasicInfoJobCode) {
        final String key = this.makeMapperKey(versionCode.toString(), mpiBasicInfoJobCode.toString(), PortDvdCode.persistence.toString());
        return Objects.requireNonNull(this.mpiBasicInfoPersistenceMapper.get(key), "mpiBasicInfoPersistenceMapper 에 저장된 PersistenceAdapter Bean 이 없습니다.");
    }
}

package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.core.merchant.enums.MpiBasicInfoJobCode;
import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.enums.MpiBasicInfoVersion;
import com.wpay.core.merchant.global.exception.CustomException;
import com.wpay.core.merchant.global.exception.ErrorCode;
import com.wpay.core.merchant.global.factory.port.BasePortFactory;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;
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
            final String key = this.makeMapperKey(persistence.getVersionCode(), persistence.getJobCode(), persistence.getPortDvdCode());
            this.mpiBasicInfoPersistenceMapper.put(key, persistence);
        }
    }

    public MpiBasicInfoPersistencePort getMpiBasicInfoPersistence(MpiBasicInfoVersion versionCode, MpiBasicInfoJobCode mpiBasicInfoJobCode) {
        final String key = this.makeMapperKey(versionCode, mpiBasicInfoJobCode, PortDvdCode.persistence);
        return Objects.requireNonNull(this.mpiBasicInfoPersistenceMapper.get(key), "mpiBasicInfoPersistenceMapper 에 저장된 PersistenceAdapter Bean 이 없습니다.");
    }
}

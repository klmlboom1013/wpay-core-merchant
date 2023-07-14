package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import com.wpay.core.merchant.global.factory.port.BasePortFactory;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;
import io.micrometer.core.instrument.util.StringUtils;
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
        if(CollectionUtils.isEmpty(mpiBasicInfoPersistenceList))
            throw new NullPointerException("MpiBasicInfoPersistence Interface 가 구현된 Persistence Adapter Bean 을 찾지 못 했습니다.");

        for(MpiBasicInfoPersistencePort persistence : mpiBasicInfoPersistenceList){
            final String key = this.makeMapperKey(persistence.getVersionCode(), persistence.getJobCode(), persistence.getPortDvdCode());
            this.mpiBasicInfoPersistenceMapper.put(key, persistence);
        }
        log.debug(">> mpiBasicInfoPersistenceMapper size - [{}]", this.mpiBasicInfoPersistenceMapper.size());
        if(this.mpiBasicInfoPersistenceMapper.size() == 0)
            throw new RuntimeException("mpiBasicInfoPersistenceMapper 에 등록할 MpiBasicInfoPersistence 구현 객체가 없습니다.");
    }

    public MpiBasicInfoPersistencePort getMpiBasicInfoPersistence(VersionCode versionCode, JobCode jobCode) {
        final String key = this.makeMapperKey(versionCode, jobCode, PortDvdCode.persistence);
        log.debug(">> make mpiBasicInfoPersistenceMapper key : {}", key);
        if(StringUtils.isBlank(key))
            throw new NullPointerException("MpiBasicInfoPersistence 를 구한현 PersistenceAdapter Bean 을 가져 오기 위한 Key 생성 실패. [key is blank]");
        return Objects.requireNonNull(this.mpiBasicInfoPersistenceMapper.get(key), "mpiBasicInfoPersistenceMapper 에 저장된 PersistenceAdapter Bean 이 없습니다.");
    }
}

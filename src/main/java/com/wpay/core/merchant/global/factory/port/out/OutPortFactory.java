package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import com.wpay.core.merchant.global.factory.port.BaseOutPort;
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
public final class OutPortFactory extends BasePortFactory {

    private final Map<String, BaseOutPort> outAdapters = new HashMap<>();

    public OutPortFactory (List<BaseOutPort> outPorts) {
        if(CollectionUtils.isEmpty(outPorts))
            throw new NullPointerException("PointService Implement class does not found.");
        for(BaseOutPort port : outPorts){
            final String key = this.makeMapperKey(port.getVersionCode(), port.getJobCode(), port.getPortDvdCode());
            if(StringUtils.isBlank(key)) continue;
            this.outAdapters.put(key, port);
        }
        log.debug(">> outAdapters size - [{}]", this.outAdapters.size());
    }

    public BasePersistencePort getPersistence(VersionCode versionCode, JobCode jobCode) {
        final String key = Objects.requireNonNull(this.makeMapperKey(versionCode, jobCode, PortDvdCode.persistence), "Factory Mapper persistence Key 생성 실패.");
        log.info("OutPortFactory Fetch Persistence Key : {}", key);
        final BasePersistencePort result = Objects.requireNonNull(
                (BasePersistencePort)this.outAdapters.get(key),"사용 가능한 Persistence 가 없습니다.");
        log.info(">> getPersistencePort Name [{}]", result.getClass().getName());
        return  result;
    }

    public BaseExternalPort getBaseExternal(VersionCode versionCode, JobCode jobCode) {
        final String key = Objects.requireNonNull(this.makeMapperKey(versionCode, jobCode, PortDvdCode.external), "Factory Mapper external Key 생성 실패.");
        log.info("OutPortFactory Fetch External Key : {}", key);
        final BaseExternalPort result = Objects.requireNonNull(
                (BaseExternalPort)this.outAdapters.get(key), "사용 가능한 External 이 없습니다.");
        log.info(">> getExternalPort Name [{}]", result.getClass().getName());
        return  result;
    }
}

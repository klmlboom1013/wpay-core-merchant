package com.wpay.core.merchant.global.factory.port.in;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import com.wpay.core.merchant.global.factory.port.BaseInPort;
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
public final class InPortFactory extends BasePortFactory {

    private final Map<String, BaseInPort> inPortUseCases = new HashMap<>();

    public InPortFactory(List<BaseInPort> inPorts) {
        if(CollectionUtils.isEmpty(inPorts))
            throw new NullPointerException("BaseInPort Interface 가 구현된 객체를 찾지 못했습니다.");
        for(BaseInPort port : inPorts) {
            final String key = this.makeMapperKey(port.getVersionCode(), port.getJobCode(), port.getPortDvdCode());
            if(StringUtils.isBlank(key)) continue;
            this.inPortUseCases.put(key, port);
        }

    }

    public BaseUseCasePort getUseCase(VersionCode versionCode, JobCode jobCode) {
        final String key = Objects.requireNonNull(this.makeMapperKey(versionCode, jobCode, PortDvdCode.usecase), "Factory Mapper usecase Key 생성 실패.");
        log.info("InPortFactory Fetch UseCase Key : {}", key);
        final BaseUseCasePort result = Objects.requireNonNull(
                (BaseUseCasePort)this.inPortUseCases.get(key), "일치 하는 UseCase 구현 객체가 없습니다.");
        log.info(">> getUseCase Name [{}]", result.getClass().getName());
        return result;
    }
}

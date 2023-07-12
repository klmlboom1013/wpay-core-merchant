package com.wpay.core.merchant.global.factory.port.in;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Factory
public final class InPortFactory {

    private final Map<String, BaseInPort> inPortUseCases = new HashMap<>();

    public InPortFactory(List<BaseInPort> inPorts) {
        if(CollectionUtils.isEmpty(inPorts))
            throw new NullPointerException("BaseInPort Interface 가 구현된 객체를 찾지 못했습니다.");
        for(BaseInPort port : inPorts)
            this.inPortUseCases.put(this.makeMapperKey(port.getVersionCode(), port.getJobCode(), port.getPortInDvdCode()), port);
    }

    public BaseUseCasePort getUseCase(VersionCode versionCode, JobCode jobCode) {
        final String key = this.makeMapperKey(versionCode, jobCode, PortInDvdCode.useCase);
        log.info("InPortFactory Fetch UseCase Key : {}", key);
        final BaseUseCasePort result = Objects.requireNonNull(
                (BaseUseCasePort)this.inPortUseCases.get(key), "일치 하는 UseCase 구현 객체가 없습니다.");
        log.info(">> getUseCase Name [{}]", result.getClass().getName());
        return result;
    }

    private String makeMapperKey (VersionCode versionCode, JobCode jobCode, PortInDvdCode portInDvdCode) {
        final String result = new StringBuilder()
                .append(versionCode.toString()).append(".")
                .append(jobCode.toString()).append(".")
                .append(portInDvdCode.toString()).toString();
        return result.toUpperCase();
    }
}

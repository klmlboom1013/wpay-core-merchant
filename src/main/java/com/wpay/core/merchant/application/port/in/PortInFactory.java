package com.wpay.core.merchant.application.port.in;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.enums.ApiVersion;
import com.wpay.core.merchant.global.enums.JobCode;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Factory
public final class PortInFactory {

    private final Map<String, BasePortInUseCase> useCases = new HashMap<>();

    public PortInFactory(List<BasePortInUseCase> useCases) {
        if(CollectionUtils.isEmpty(useCases))
            throw new IllegalArgumentException("BasePortInUseCase Implement class does not found.");
        for(BasePortInUseCase useCase : useCases)
            this.useCases.put((new Type(useCase.getApiType(), useCase.getVersion()).getKey()), useCase);
    }

    public BasePortInUseCase getUseCase(final JobCode jobCode, final ApiVersion apiVersion) {
        return this.useCases.get(new StringBuilder(jobCode.getCode()).append(apiVersion.toString()).toString());
    }

    @Value
    @AllArgsConstructor
    private static class Type {
        JobCode jobCode;
        ApiVersion apiVersion;
        String getKey() {
            return new StringBuilder(jobCode.getCode()).append(apiVersion.toString()).toString();
        }
    }
}

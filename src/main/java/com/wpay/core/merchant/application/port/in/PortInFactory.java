package com.wpay.core.merchant.application.port.in;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.enums.JobCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Factory
public final class PortInFactory {

    private final Map<JobCode, BasePortInUseCase> useCases = new HashMap<>();

    public PortInFactory(List<BasePortInUseCase> useCases) {
        if(CollectionUtils.isEmpty(useCases)) {
            throw new IllegalArgumentException("BasePortInUseCase Implement class does not found.");
        }

        for(BasePortInUseCase useCase : useCases) {
            this.useCases.put(useCase.getApiType(), useCase);
        }
    }

    public BasePortInUseCase getUseCase(final JobCode jobCode) {
        return this.useCases.get(jobCode);
    }
}

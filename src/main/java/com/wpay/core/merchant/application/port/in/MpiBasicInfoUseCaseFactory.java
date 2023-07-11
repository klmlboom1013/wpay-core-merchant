package com.wpay.core.merchant.application.port.in;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.enums.ApiVersion;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Factory
public final class MpiBasicInfoUseCaseFactory {

    private final Map<ApiVersion, SendMpiBasicInfoUseCase> useCases = new HashMap<>();

    public MpiBasicInfoUseCaseFactory(List<SendMpiBasicInfoUseCase> useCases) {
        if(CollectionUtils.isEmpty(useCases))
            throw new NullPointerException("BasePortInUseCase Interface 가 구현된 객체를 찾지 못했습니다.");
        for(SendMpiBasicInfoUseCase useCase : useCases)
            this.useCases.put(useCase.getVersion(), useCase);
    }

    public SendMpiBasicInfoUseCase getUseCase(final ApiVersion apiVersion) {
        return Objects.requireNonNull(
                this.useCases.get(apiVersion),
                "일치 하는 UseCase 가 없습니다. API URL version 과 Require Data 정보를 다시 확인 바랍니다.");
    }
}

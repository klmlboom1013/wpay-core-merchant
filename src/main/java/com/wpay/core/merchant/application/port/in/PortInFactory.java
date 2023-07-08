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
import java.util.Objects;

@Log4j2
@Factory
public final class PortInFactory {

    private final Map<String, BasePortInUseCase> useCases = new HashMap<>();

    public PortInFactory(List<BasePortInUseCase> useCases) {
        if(CollectionUtils.isEmpty(useCases))
            throw new NullPointerException("BasePortInUseCase Interface 가 구현된 객체를 찾지 못했 습니다.");
        for(BasePortInUseCase useCase : useCases)
            this.useCases.put(makeMapperKey(useCase.getApiType().getCode(), useCase.getVersion().toString()), useCase);
    }

    public BasePortInUseCase getUseCase(final JobCode jobCode, final String version) {
        final BasePortInUseCase useCase =
                this.useCases.get(
                        new StringBuilder(jobCode.getCode())
                                .append(ApiVersion.getInstance(version))
                                .toString());
        if(Objects.isNull(useCase)) {
            throw new NullPointerException("일치 하는 UseCase 가 없습니다. API URL version 과 Require Data 정보를 다시 확인 바랍니다.");
        }
        return this.useCases.get(makeMapperKey(useCase.getApiType().getCode(), useCase.getVersion().toString()));
    }

    private String makeMapperKey (String... strs) {
        final StringBuilder result = new StringBuilder();
        for(String s : strs) result.append(s);
        return result.toString();
    }
}

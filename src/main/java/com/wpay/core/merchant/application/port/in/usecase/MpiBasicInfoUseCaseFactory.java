package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.annotation.UseCase;
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
public final class MpiBasicInfoUseCaseFactory extends BasePortFactory {

    private final Map<String, MpiBasicInfoUseCase> mpiBasicInfoUseCaseMapper = new HashMap<>();

    public MpiBasicInfoUseCaseFactory(List<MpiBasicInfoUseCase> mpiBasicInfoUseCases) {
        if(CollectionUtils.isEmpty(mpiBasicInfoUseCases))
            throw new NullPointerException("MpiBasicInfoUseCase Interface 가 구현된 UseCase Bean 을 찾지 못 했습니다.");

        for(MpiBasicInfoUseCase useCase : mpiBasicInfoUseCases) {
            /* @UseCase Annotation 이 선언 되어 있는지 확인. 없으면  continue; */
            if(Objects.isNull(useCase.getClass().getAnnotation(UseCase.class))) continue;
            /* MpiBasicInfoUseCase 를 구현한 Bean 의  PortDvdCode 값 검증. */
            if(Boolean.FALSE.equals(PortDvdCode.usecase.equals(useCase.getPortDvdCode()))) continue;
            /* MpiBasicInfoUseCase 를 구현한 Bean 의 JobCode 일치 여부 검증 */
            if(Boolean.FALSE.equals(JobCode.JOB_CODE_20.equals(useCase.getJobCode()))) continue;
            /* makeMapperKey 수행 후 받은 key 값이 Blank 검증 true 면 continue; */
            final String key = this.makeMapperKey(useCase.getVersionCode(), useCase.getJobCode(), useCase.getPortDvdCode());
            if(StringUtils.isBlank(key)) continue;
            this.mpiBasicInfoUseCaseMapper.put(key, useCase);
        }
        log.debug(">> mpiBasicInfoUseCaseMapper size - [{}]", this.mpiBasicInfoUseCaseMapper.size());
    }

    /**
     * MpiBasicInfoUseCase 를 구한현 UseCase Bean 을 리턴 한다.
     * @param versionCode API 버전 코드
     * @param jobCode API 업무 구분 코드
     * @return MpiBasicInfoUseCase
     */
    public MpiBasicInfoUseCase getMpiBasicInfoUseCase (VersionCode versionCode, JobCode jobCode) {
        final String key = this.makeMapperKey(versionCode, jobCode, PortDvdCode.usecase);
        if(StringUtils.isBlank(key))
            throw new NullPointerException("MpiBasicInfoUseCase 를 구한현 UseCase Bean 을 가져 오기 위한 Key 생성 실패. [key is blank]");
        return Objects.requireNonNull(this.mpiBasicInfoUseCaseMapper.get(key), "mpiBasicInfoUseCaseMapper 에 저장된 UseCase Bean 이 없습니다.");
    }
}

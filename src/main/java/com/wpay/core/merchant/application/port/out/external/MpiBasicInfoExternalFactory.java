package com.wpay.core.merchant.application.port.out.external;

import com.wpay.core.merchant.global.annotation.ExternalAdapter;
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
public final class MpiBasicInfoExternalFactory extends BasePortFactory {

    private final Map<String, MpiBasicInfoExternal> mpiBasicInfoExternalMapper = new HashMap<>();

    public MpiBasicInfoExternalFactory(List<MpiBasicInfoExternal> mpiBasicInfoExternals) {
        if(CollectionUtils.isEmpty(mpiBasicInfoExternals))
            throw new NullPointerException("MpiBasicInfoExternal Interface 가 구현된 Persistence Adapter Bean 을 찾지 못 했습니다.");

        for(MpiBasicInfoExternal external : mpiBasicInfoExternals){
            /* @PersistenceAdapter Annotation 이 선언 되어 있는지 확인. 없으면  continue; */
            if(Objects.isNull(external.getClass().getAnnotation(ExternalAdapter.class))) continue;
            /* MpiBasicInfoPersistence 를 구현한 Bean 의  PortDvdCode 값 검증. */
            if(Boolean.FALSE.equals(PortDvdCode.persistence.equals(external.getPortDvdCode()))) continue;
            /* MpiBasicInfoPersistence 를 구현한 Bean 의 JobCode 일치 여부 검증 */
            if(Boolean.FALSE.equals(JobCode.JOB_CODE_20.equals(external.getJobCode()))) continue;
            /* makeMapperKey 수행 후 받은 key 값이 Blank 검증 true 면 continue; */
            final String key = this.makeMapperKey(external.getVersionCode(), external.getJobCode(), external.getPortDvdCode());
            if(StringUtils.isBlank(key)) continue;
            this.mpiBasicInfoExternalMapper.put(key, external);
        }
        log.debug(">> mpiBasicInfoExternalMapper size - [{}]", this.mpiBasicInfoExternalMapper.size());
    }

    public MpiBasicInfoExternal getMpiBasicInfoExternal(VersionCode versionCode, JobCode jobCode) {
        final String key = this.makeMapperKey(versionCode, jobCode, PortDvdCode.usecase);
        log.debug(">> make MpiBasicInfoExternalMapper key : {}", key);
        if(StringUtils.isBlank(key))
            throw new NullPointerException("MpiBasicInfoExternal 를 구한현 ExternalAdapter Bean 을 가져 오기 위한 Key 생성 실패. [key is blank]");
        return Objects.requireNonNull(this.mpiBasicInfoExternalMapper.get(key), "mpiBasicInfoExternalMapper 에 저장된 ExternalAdapter Bean 이 없습니다.");
    }
}

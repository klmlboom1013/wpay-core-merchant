package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.core.merchant.global.annotation.Factory;
import com.wpay.core.merchant.global.annotation.PersistenceAdapter;
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

    private final Map<String, MpiBasicInfoPersistence> mpiBasicInfoPersistenceMapper = new HashMap<>();

    public MpiBasicInfoPersistenceFactory(List<MpiBasicInfoPersistence> mpiBasicInfoPersistenceList) {
        if(CollectionUtils.isEmpty(mpiBasicInfoPersistenceList))
            throw new NullPointerException("MpiBasicInfoPersistence Interface 가 구현된 Persistence Adapter Bean 을 찾지 못 했습니다.");

        for(MpiBasicInfoPersistence persistence : mpiBasicInfoPersistenceList){
            /* @PersistenceAdapter Annotation 이 선언 되어 있는지 확인. 없으면  continue; */
            if(Objects.isNull(persistence.getClass().getAnnotation(PersistenceAdapter.class))) continue;
            /* MpiBasicInfoPersistence 를 구현한 Bean 의  PortDvdCode 값 검증. */
            if(Boolean.FALSE.equals(PortDvdCode.persistence.equals(persistence.getPortDvdCode()))) continue;
            /* MpiBasicInfoPersistence 를 구현한 Bean 의 JobCode 일치 여부 검증 */
            if(Boolean.FALSE.equals(JobCode.JOB_CODE_20.equals(persistence.getJobCode()))) continue;
            /* makeMapperKey 수행 후 받은 key 값이 Blank 검증 true 면 continue; */
            final String key = this.makeMapperKey(persistence.getVersionCode(), persistence.getJobCode(), persistence.getPortDvdCode());
            if(StringUtils.isBlank(key)) continue;
            this.mpiBasicInfoPersistenceMapper.put(key, persistence);
        }
        log.debug(">> mpiBasicInfoPersistenceMapper size - [{}]", this.mpiBasicInfoPersistenceMapper.size());
    }

    public MpiBasicInfoPersistence getMpiBasicInfoPersistence(VersionCode versionCode, JobCode jobCode) {
        final String key = this.makeMapperKey(versionCode, jobCode, PortDvdCode.usecase);
        log.debug(">> make mpiBasicInfoPersistenceMapper key : {}", key);
        if(StringUtils.isBlank(key))
            throw new NullPointerException("MpiBasicInfoPersistence 를 구한현 PersistenceAdapter Bean 을 가져 오기 위한 Key 생성 실패. [key is blank]");
        return Objects.requireNonNull(this.mpiBasicInfoPersistenceMapper.get(key), "mpiBasicInfoPersistenceMapper 에 저장된 PersistenceAdapter Bean 이 없습니다.");
    }
}

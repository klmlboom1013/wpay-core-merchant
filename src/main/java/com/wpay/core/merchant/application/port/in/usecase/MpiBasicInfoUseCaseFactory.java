package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.common.global.annotation.Factory;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.factory.port.BasePortFactory;
import com.wpay.common.global.factory.port.PortDvdCode;
import com.wpay.core.merchant.global.enums.MpiBasicInfoVersion;
import com.wpay.core.merchant.global.enums.MpiBasicInfoJobCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Factory
public final class MpiBasicInfoUseCaseFactory extends BasePortFactory {

    private final Map<String, MpiBasicInfoUseCasePort> mpiBasicInfoUseCaseMapper = new HashMap<>();

    public MpiBasicInfoUseCaseFactory(List<MpiBasicInfoUseCasePort> mpiBasicInfoUseCases) {
        if(CollectionUtils.isEmpty(mpiBasicInfoUseCases)){
            log.error("MpiBasicInfoUseCase Interface 가 구현된 UseCase Bean 을 찾지 못 했습니다.");
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        for(MpiBasicInfoUseCasePort useCase : mpiBasicInfoUseCases) {
            final String key = this.makeMapperKey(
                    useCase.getVersionCode().toString(), useCase.getJobCode().toString(), useCase.getPortDvdCode().toString());
            this.mpiBasicInfoUseCaseMapper.put(key, useCase);
        }
    }

    /**
     * MpiBasicInfoUseCase 를 구한현 UseCase Bean 을 리턴 한다.
     * @param versionCode API 버전 코드
     * @param mpiBasicInfoJobCode API 업무 구분 코드
     * @return MpiBasicInfoUseCase
     */
    public MpiBasicInfoUseCasePort getMpiBasicInfoUseCase (MpiBasicInfoVersion versionCode, MpiBasicInfoJobCode mpiBasicInfoJobCode) {
        final String key = this.makeMapperKey(versionCode.toString(), mpiBasicInfoJobCode.toString(), PortDvdCode.usecase.toString());
        return Objects.requireNonNull(this.mpiBasicInfoUseCaseMapper.get(key), "mpiBasicInfoUseCaseMapper 에 저장된 UseCase Bean 이 없습니다.");
    }
}

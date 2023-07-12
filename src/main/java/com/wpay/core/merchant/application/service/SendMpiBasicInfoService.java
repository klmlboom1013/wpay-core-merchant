package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.application.port.in.usecase.SendMpiBasicInfoUseCase;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.global.factory.port.out.BasePersistencePort;
import com.wpay.core.merchant.global.factory.port.out.OutPortFactory;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.annotation.UseCase;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.VersionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class SendMpiBasicInfoService implements SendMpiBasicInfoUseCase {

    private final OutPortFactory outPortFactory;

    @Override public final VersionCode getVersionCode() { return VersionCode.v1; }

    @Override
    public BaseResponse searchMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo) {
        log.info("Set ActivityMpiBasicInfo - [{}]", activityMpiBasicInfo);
        final MpiBasicInfo mpiBasicInfo = Objects.requireNonNull(
                (MpiBasicInfo) this.getPersistencePort().loadActivitiesRun(activityMpiBasicInfo),
                "가맹점 기준 정보 조회 Out Port 를 찾지 못 했습니다.");
        return BaseResponse.builder().httpStatus(HttpStatus.OK)
                .data(mpiBasicInfo).build();
    }

    private BasePersistencePort getPersistencePort (){
        return this.outPortFactory.getPersistence(this.getVersionCode(), this.getJobCode());
    }
}

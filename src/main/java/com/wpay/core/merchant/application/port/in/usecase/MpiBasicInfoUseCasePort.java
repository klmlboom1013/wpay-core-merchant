package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.in.UseCasePort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;

import java.util.Objects;

public interface MpiBasicInfoUseCasePort extends UseCasePort {


    @Override
    default JobCodes getJobCode(){
        return JobCodes.JOB_CODE_20;
    }

    /**
     *  MPI 기준 정보 조회 실행.
     */
    @Override
    default BaseResponse execute (SelfValidating<?> selfValidating){

        final ActivityMpiBasicInfo activityMpiBasicInfo = ActivityMpiBasicInfo.builder()
                .baseCommand((BaseCommand<?>) selfValidating)
                .build();

        final BaseResponse searchDBResult = this.searchMpiBasicInfoUseCase(activityMpiBasicInfo);
        if(Objects.nonNull(searchDBResult))
            return searchDBResult;

        return sendMpiBasicInfoUseCase(activityMpiBasicInfo);
    }

    /** DB 에서 이전 MPI 기준 정보 조회 연동 이력이 있으면 Return 구현 */
    BaseResponse searchMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo);


    /** DB 에  MPI 기준 정보 조회 연동 이력이 없으면 MPI 연동 시작. */
    BaseResponse sendMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo);


}

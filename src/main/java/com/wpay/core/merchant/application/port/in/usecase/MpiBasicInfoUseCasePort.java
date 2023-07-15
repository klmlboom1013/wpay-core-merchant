package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.core.merchant.domain.ActivityMpiTrns;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.dto.SelfValidating;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.application.service.SearchMpiBasicInfoOption;
import com.wpay.core.merchant.global.factory.port.in.UseCasePort;

import java.util.Objects;

public interface MpiBasicInfoUseCasePort extends UseCasePort {

    @Override default JobCode getJobCode() { return JobCode.JOB_CODE_20; }

    /**
     *  MPI 기준 정보 조회 실행.
     */
    @Override
    default BaseResponse execute (SelfValidating<?> selfValidating){
        final MpiBasicInfoCommand mpiBasicInfoCommand = (MpiBasicInfoCommand) selfValidating;

        final ActivityMpiTrns activityMpiTrns = ActivityMpiTrns.builder()
                .option(SearchMpiBasicInfoOption.getInstance(mpiBasicInfoCommand.getOption().toString()))
                .jobCode(MpiBasicInfoCommand.jobCode)
                .mid(mpiBasicInfoCommand.getMid())
                .wtid(mpiBasicInfoCommand.getWtid())
                .serverName(mpiBasicInfoCommand.getServerName())
                .build();

        final BaseResponse searchDBResult = this.searchMpiBasicInfoUseCase(activityMpiTrns);
        if(Objects.nonNull(searchDBResult))
            return searchDBResult;

        return sendMpiBasicInfoUseCase(activityMpiTrns);
    }

    /** DB 에서 이전 MPI 기준 정보 조회 연동 이력이 있으면 Return 구현 */
    BaseResponse searchMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns);


    /** DB 에  MPI 기준 정보 조회 연동 이력이 없으면 MPI 연동 시작. */
    BaseResponse sendMpiBasicInfoUseCase (ActivityMpiTrns activityMpiTrns);
}

package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.dto.SelfValidating;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.SearchMpiBasicInfoOption;
import com.wpay.core.merchant.global.factory.port.in.BaseUseCasePort;

public interface SendMpiBasicInfoUseCase extends BaseUseCasePort {

    @Override default JobCode getJobCode() { return JobCode.SendMpiBasicInfo; }

    @Override
    default BaseResponse execute (SelfValidating<?> selfValidating){
        final MpiBasicInfoCommand mpiBasicInfoCommand = (MpiBasicInfoCommand) selfValidating;
        return this.searchMpiBasicInfoUseCase(ActivityMpiBasicInfo.builder()
                .option(SearchMpiBasicInfoOption.getInstance(mpiBasicInfoCommand.getOption().toString()))
                .jobCode(MpiBasicInfoCommand.jobCode)
                .mid(mpiBasicInfoCommand.getMid())
                .wtid(mpiBasicInfoCommand.getWtid())
                .build());
    }

    /** Business 구현 */
    BaseResponse searchMpiBasicInfoUseCase (ActivityMpiBasicInfo activityMpiBasicInfo);
}

package com.wpay.core.merchant.application.port.in;

import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.ApiVersion;
import com.wpay.core.merchant.global.enums.SearchMpiBasicInfoOption;

public interface SendMpiBasicInfoUseCase {

    ApiVersion getVersion();

    /** Business 구현 */
    BaseResponse execute (MpiBasicInfoCommand mpiBasicInfoCommand);

    default ActivityMpiBasicInfo makeActivityMpiBasicInfo(MpiBasicInfoCommand mpiBasicInfoCommand) {
        return ActivityMpiBasicInfo.builder()
                .option(SearchMpiBasicInfoOption.getInstance(mpiBasicInfoCommand.getOption().toString()))
                .jobCode(MpiBasicInfoCommand.jobCode)
                .mid(mpiBasicInfoCommand.getMid())
                .wtid(mpiBasicInfoCommand.getWtid())
                .build();
    }
}

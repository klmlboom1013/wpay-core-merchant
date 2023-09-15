package com.wpay.core.merchant.application.port.in.dto;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCaseVersion;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class MpiBasicInfoCommand extends BaseCommand<MpiBasicInfoCommand> {

    @Override
    public void validateSelf() {
        super.validateSelf();
    }

    @Override
    public void checkVersion(String version) {
        MpiBasicInfoUseCaseVersion.getInstance(version);
    }

    @Override
    public JobCodes getJobCodes() {
        return JobCodes.JOB_CODE_20;
    }
}

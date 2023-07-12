package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.core.merchant.global.dto.SelfValidating;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.SearchMpiBasicInfoOption;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Getter
@EqualsAndHashCode(callSuper = false)
public class MpiBasicInfoCommand extends SelfValidating<MpiBasicInfoCommand> {

    public static final JobCode jobCode = JobCode.SendMpiBasicInfo;

    @NotNull(message = "option 값이 Null 이면 안됩니다.")
    @Size(min = 2, max = 2, message = "option 값 길이는 2 이어야 합니다.")
    String option;

    @NotNull(message = "mid 값이 Null 이면 안됩니다.")
    @Size(min=10, max=20, message = "mid 값 길이는 10 부터 20 까지 입니다.")
    String mid;

    @NotNull(message = "wtid 값이 Null 이면 안됩니다.")
    @Size(max = 64, message = "wtid 값 길이는 64를 초과 하면 안됩니다.")
    String wtid;

    @Override
    public void validateSelf() {
        super.validateSelf();
    }

    public SearchMpiBasicInfoOption getOption() {
        return SearchMpiBasicInfoOption.getInstance(this.option.toUpperCase());
    }
}

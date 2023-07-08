package com.wpay.core.merchant.adapter.in.dto;

import com.wpay.core.merchant.global.dto.SelfValidating;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.MerchantInfoSearchOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MerchantInfo extends SelfValidating<MerchantInfo> {

    public static final JobCode jobCode = JobCode.SendMpiBasicInfo;

    @NotNull
    MerchantInfoSearchOptions option;

    @NotNull
    @Size(min=10, max=20)
    String mid;

    @Builder
    public MerchantInfo(@NonNull String mid, @NonNull String option) {
        this.option = MerchantInfoSearchOptions.getInstance(option.toUpperCase());
        this.mid = mid;
        this.validateSelf();
    }
}

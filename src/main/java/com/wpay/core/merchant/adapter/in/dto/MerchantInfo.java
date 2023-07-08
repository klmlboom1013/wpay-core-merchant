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
    @Size(min = 2, max = 2)
    MerchantInfoSearchOptions option;

    @NotNull
    @Size(min=10, max=20)
    String mid;

    @NotNull
    @Size(max = 64)
    String wtid;

    @NotNull
    @Size(max = 100)
    String wpayUserKey;

    @Builder
    public MerchantInfo(String mid, String option, String wtid, String wpayUserKey) {
        this.option = MerchantInfoSearchOptions.getInstance(option.toUpperCase());
        this.mid = mid;
        this.wtid = wtid;
        this.wpayUserKey = wpayUserKey;
        this.validateSelf();
    }
}

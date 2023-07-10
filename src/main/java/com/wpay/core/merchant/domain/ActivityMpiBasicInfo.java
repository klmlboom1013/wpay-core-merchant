package com.wpay.core.merchant.domain;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.SearchMpiBasicInfoOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;


@Value
@Builder
@Getter
@AllArgsConstructor
public class ActivityMpiBasicInfo {

    MpiTrnsId mpiTrnsId;

    JobCode jobCode;
    SearchMpiBasicInfoOption option;

    String mid;

    @Value
    @Getter
    @Builder
    public static class MpiTrnsId {
        String wtid;
        Long srlno;
    }
}

package com.wpay.core.merchant.domain;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.application.service.SearchMpiBasicInfoOption;
import lombok.*;


@Value
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityMpiTrns {

    @NonNull MpiTrnsId mpiTrnsId;
    @NonNull JobCode jobCode;
    @NonNull SearchMpiBasicInfoOption option;
    @NonNull String mid;

    @Builder
    public ActivityMpiTrns(JobCode jobCode, SearchMpiBasicInfoOption option, String mid, String wtid) {
        this.jobCode = jobCode;
        this.option = option;
        this.mid = mid;
        this.mpiTrnsId = MpiTrnsId.builder().wtid(wtid).build();
    }

    @Value
    @Getter
    @Builder
    public static class MpiTrnsId {
        String wtid;
        Long srlno;
    }
}

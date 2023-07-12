package com.wpay.core.merchant.domain;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.application.service.SearchMpiBasicInfoOption;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;


@Value
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityMpiBasicInfo {

    MpiTrnsId mpiTrnsId;

    JobCode jobCode;
    SearchMpiBasicInfoOption option;

    String mid;

    @Builder
    public ActivityMpiBasicInfo(JobCode jobCode, SearchMpiBasicInfoOption option, String mid, String wtid) {
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

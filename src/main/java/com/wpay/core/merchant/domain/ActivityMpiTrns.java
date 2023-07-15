package com.wpay.core.merchant.domain;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.application.service.SearchMpiBasicInfoOption;
import lombok.*;


@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityMpiTrns {
    private final JobCode jobCode;
    private final String mid;
    private final String serverName;
    private final SearchMpiBasicInfoOption option;

    @Setter private MpiTrnsId mpiTrnsId;
    @Setter private ActivitySendMpi activitySendMpi;

    @Builder
    public ActivityMpiTrns(JobCode jobCode, SearchMpiBasicInfoOption option, String mid, String wtid, String serverName) {
        this.jobCode = jobCode;
        this.option = option;
        this.mid = mid;
        this.mpiTrnsId = MpiTrnsId.builder().wtid(wtid).build();
        this.serverName = serverName;
    }

    /**
     * MpiTrns Key
     */
    @Value
    @Getter
    @Builder
    public static class MpiTrnsId {
        String wtid;
        Long srlno;
    }

    /**
     * Save MpiTrns
     */
    @Value
    @Getter
    @Builder
    public static class ActivitySendMpi {
        String connUrl;
        String payRsltCd;
        String rspsGrmConts;
    }
}

package com.wpay.core.merchant.trnsmpi.domain;

import com.wpay.common.global.enums.JobCodes;
import lombok.*;


@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityMpiBasicInfo {
    private final JobCodes jobCodes;
    private final String mid;
    private final String serverName;

    @Setter private MpiTrnsId mpiTrnsId;
    @Setter private ActivitySendMpi activitySendMpi;

    @Builder
    public ActivityMpiBasicInfo(JobCodes jobCodes, String mid, String wtid, String serverName) {
        this.jobCodes = jobCodes;
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

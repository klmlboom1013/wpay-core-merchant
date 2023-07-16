package com.wpay.core.merchant.domain;

import com.wpay.core.merchant.enums.MpiBasicInfoJobCode;
import lombok.*;


@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityMpiTrns {
    private final MpiBasicInfoJobCode mpiBasicInfoJobCode;
    private final String mid;
    private final String serverName;

    @Setter private MpiTrnsId mpiTrnsId;
    @Setter private ActivitySendMpi activitySendMpi;

    @Builder
    public ActivityMpiTrns(MpiBasicInfoJobCode mpiBasicInfoJobCode, String mid, String wtid, String serverName) {
        this.mpiBasicInfoJobCode = mpiBasicInfoJobCode;
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

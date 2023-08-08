package com.wpay.core.merchant.trnsmpi.domain;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.enums.JobCodes;
import lombok.*;


@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityMpiBasicInfo extends SelfValidating<ActivityMpiBasicInfo> {
    private final JobCodes jobCodes;
    private final String mid;
    private final String serverName;

    @Setter private MpiTrnsId mpiTrnsId;
    @Setter private ActivitySendMpi activitySendMpi;

    @Builder
    public ActivityMpiBasicInfo(BaseCommand<?> baseCommand) {
        this.jobCodes = baseCommand.getJobCodes();
        this.mid = baseCommand.getMid();
        this.mpiTrnsId = MpiTrnsId.builder().wtid(baseCommand.getWtid()).build();
        this.serverName = baseCommand.getServerName();
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

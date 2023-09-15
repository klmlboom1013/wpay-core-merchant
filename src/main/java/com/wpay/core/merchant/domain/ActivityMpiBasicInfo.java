package com.wpay.core.merchant.domain;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.enums.JobCodes;
import lombok.*;


@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityMpiBasicInfo extends SelfValidating<ActivityMpiBasicInfo> {
    private final JobCodes jobCodes;

    @Setter private String jnoffcId;
    @Setter private String idcDvdCd;
    @Setter private MpiTrnsId mpiTrnsId;
    @Setter private ActivitySendMpi activitySendMpi;

    @Builder
    public ActivityMpiBasicInfo(BaseCommand<?> baseCommand) {
        this.jobCodes = baseCommand.getJobCodes();
        this.jnoffcId = baseCommand.getJnoffcId();
        this.idcDvdCd = baseCommand.getIdcDvdCd();
        this.mpiTrnsId = MpiTrnsId.builder().wtid(baseCommand.getWtid()).build();
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

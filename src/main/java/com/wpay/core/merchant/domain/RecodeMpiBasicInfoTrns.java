package com.wpay.core.merchant.domain;

import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.functions.DataFunctions;
import com.wpay.common.global.functions.DateFunctions;
import com.wpay.core.merchant.application.port.out.dto.MpiBasicInfoMapper;
import lombok.*;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class RecodeMpiBasicInfoTrns extends SelfValidating<RecodeMpiBasicInfoTrns> {

    private final String wtid;
    private final Long srlno;
    private final String jnoffcId;
    private final String idcDvdCd;
    private final String jobDvdCd;
    private final String otransWtid;
    private final String regiDt;
    private final String regiTm;

    private String connUrl;
    private String payRsltCd;
    private String rsltMsgConts;
    private String rspsGrmConts;
    private String chngDt;
    private String chngTm;

    @Builder
    public RecodeMpiBasicInfoTrns(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {
        this.wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        this.jobDvdCd = activityMpiBasicInfo.getJobCodes().getCode();

        this.jnoffcId = activityMpiBasicInfo.getJnoffcId();
        this.idcDvdCd = activityMpiBasicInfo.getIdcDvdCd();

        this.otransWtid = this.wtid;

        this.srlno = DataFunctions.makeSrlno.apply(new Date());
        final String[] datetime = DateFunctions.getDateAndTime.apply(new Date());
        this.regiDt = datetime[0];
        this.regiTm = datetime[1];
    }

    public void setResultMapper (@NonNull MpiBasicInfoMapper mpiBasicInfoMapper){
        this.connUrl = mpiBasicInfoMapper.getUrl();
        this.payRsltCd = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getResultCode();
        this.rsltMsgConts = mpiBasicInfoMapper.getSendMpiBasicInfoResult().getErrorMsg();
        this.rspsGrmConts = mpiBasicInfoMapper.getMessage();

        final String[] datetime = DateFunctions.getDateAndTime.apply(new Date());
        this.chngDt = datetime[0];
        this.chngTm = datetime[1];
    }
}

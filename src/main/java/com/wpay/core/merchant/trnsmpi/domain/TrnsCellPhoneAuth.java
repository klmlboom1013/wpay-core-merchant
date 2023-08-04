package com.wpay.core.merchant.trnsmpi.domain;

import com.wpay.common.global.common.DateFunctions;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.core.merchant.global.JobCodeException;
import com.wpay.core.merchant.global.enums.MobiliansMsgType;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MobiliansCellPhoneAuthMapper;
import lombok.*;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
public class TrnsCellPhoneAuth {
    private String wtid;
    private String idcDvdCd;
    private String jobDvdCd;
    private String regiDt;
    private String regiTm;
    private String jnoffcId;
    private String authReqTypeCd; // 모빌리언스 전문 코드 (61:SMS요청, 63:인증번호확인)
    private String mmtTccoDvdCd; // 통신사
    private String buyerNm;
    private String ecdCphno;
    private String ecdBthDt;
    private String mobilTransNo; // mobilId

    private String payRsltCd;
    private String rsltMsgConts;
    private String rspsGrmConts;
    private String connUrl;

    @Builder
    public TrnsCellPhoneAuth(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth,
                             @NonNull MobiliansCellPhoneAuthMapper mobiliansCellPhoneAuthMapper) {
        this.wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        this.idcDvdCd = activityCellPhoneAuth.getServerName();
        this.jobDvdCd = activityCellPhoneAuth.getJobCodes().getCode();

        final String[] datetime = DateFunctions.getDateAndTime.apply(new Date());
        this.regiDt = datetime[0];
        this.regiTm = datetime[1];

        this.jnoffcId = activityCellPhoneAuth.getMid();

        if ((JobCodes.JOB_CODE_18.equals(activityCellPhoneAuth.getJobCodes())))
            this.authReqTypeCd = MobiliansMsgType.SEND_SMS.getCode();
        else if ((JobCodes.JOB_CODE_19.equals(activityCellPhoneAuth.getJobCodes())))
            this.authReqTypeCd = MobiliansMsgType.CERTIFICATION.getCode();
        else
            throw new JobCodeException(activityCellPhoneAuth.getMpiTrnsId().getWtid(), activityCellPhoneAuth.getMid());

        this.mmtTccoDvdCd = mmtTccoDvdCd;
        this.buyerNm = buyerNm;
        this.ecdCphno = ecdCphno;
        this.ecdBthDt = ecdBthDt;
        this.mobilTransNo = mobilTransNo;
        this.payRsltCd = payRsltCd;
        this.rsltMsgConts = rsltMsgConts;
        this.rspsGrmConts = rspsGrmConts;
        this.connUrl = connUrl;

    }
}

package com.wpay.core.merchant.trnsmpi.domain;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.exception.customs.JobCodeException;
import com.wpay.common.global.functions.DateFunctions;
import com.wpay.core.merchant.global.enums.MobiliansMsgType;
import lombok.*;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityTrnsCellPhoneAuth extends SelfValidating<ActivityTrnsCellPhoneAuth> {
    private final String wtid;
    private final String idcDvdCd;
    private final String jobDvdCd;
    private final String regiDt;
    private final String regiTm;
    private final String jnoffcId;
    private final String authReqTypeCd; // 모빌리언스 전문 코드 (61:SMS요청, 63:인증번호확인)
    private final String mmtTccoDvdCd; // 통신사
    private final String buyerNm;

    @Crypto(type = Crypto.Type.ENCRYPTION, algorithm = Crypto.Algorithm.SEED, cryptoKey = Crypto.CryptoKey.DB)
    private final String ecdCphno;
    @Crypto(type = Crypto.Type.ENCRYPTION, algorithm = Crypto.Algorithm.SEED, cryptoKey = Crypto.CryptoKey.DB)
    private final String ecdBthDt;

    private final String otrnsWtid;

    private String mobilTransNo; // mobilId
    private String payRsltCd;
    private String rsltMsgConts;
    private String rspsGrmConts;
    private String connUrl;
    private String chngDt;
    private String chngTm;

    /**
     * 모빌리언스 연동 요청 데이터 세팅
     */
    @Builder
    public ActivityTrnsCellPhoneAuth(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        this.wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        this.otrnsWtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        this.idcDvdCd = activityCellPhoneAuth.getServerName();
        this.jobDvdCd = activityCellPhoneAuth.getJobCodes().getCode();
        this.jnoffcId = activityCellPhoneAuth.getMid();
        this.mmtTccoDvdCd = activityCellPhoneAuth.getSendSmsAuthNumb().getCommandId().name();
        this.buyerNm = activityCellPhoneAuth.getSendSmsAuthNumb().getUserNm();
        this.ecdCphno = activityCellPhoneAuth.getSendSmsAuthNumb().getPhoneNo();
        this.ecdBthDt = activityCellPhoneAuth.getSendSmsAuthNumb().getUserSocNo();

        final String[] datetime = DateFunctions.getDateAndTime.apply(new Date());
        this.regiDt = datetime[0];
        this.regiTm = datetime[1];

        if ((JobCodes.JOB_CODE_18.equals(activityCellPhoneAuth.getJobCodes())))
            this.authReqTypeCd = MobiliansMsgType.SEND_SMS.getCode();
        else if ((JobCodes.JOB_CODE_19.equals(activityCellPhoneAuth.getJobCodes())))
            this.authReqTypeCd = MobiliansMsgType.CERTIFICATION.getCode();
        else
            throw new JobCodeException(activityCellPhoneAuth.getMpiTrnsId().getWtid(), activityCellPhoneAuth.getMid());
    }

    /**
     * 모빌리언스 연동 응답 데이터 세팅
     */
    public void setResultMapper (@NonNull ActivityCellPhoneAuth activityCellPhoneAuth){
        this.mobilTransNo = activityCellPhoneAuth.getReceiveMobiliansCellPhoneAuth().getMobileId();
        this.payRsltCd = activityCellPhoneAuth.getReceiveMobiliansCellPhoneAuth().getResultCode();
        this.rsltMsgConts = activityCellPhoneAuth.getReceiveMobiliansCellPhoneAuth().getResultMsg();
        this.rspsGrmConts = activityCellPhoneAuth.getReceiveMobiliansCellPhoneAuth().getRecvConts();
        this.connUrl = activityCellPhoneAuth.getReceiveMobiliansCellPhoneAuth().getConnUrl();

        final String[] datetime = DateFunctions.getDateAndTime.apply(new Date());
        this.chngDt = datetime[0];
        this.chngTm = datetime[1];
    }
}

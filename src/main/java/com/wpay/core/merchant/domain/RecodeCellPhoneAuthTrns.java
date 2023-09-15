package com.wpay.core.merchant.domain;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.functions.DataFunctions;
import com.wpay.common.global.functions.DateFunctions;
import com.wpay.core.merchant.global.enums.MobiliansMsgType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class RecodeCellPhoneAuthTrns extends SelfValidating<RecodeCellPhoneAuthTrns> {
    private final String wtid;
    private final Long srlno;
    private final String idcDvdCd;
    private final String jobDvdCd;
    private final String regiDt;
    private final String regiTm;
    private final String jnoffcId;
    private final String authReqTypeCd; // 모빌리언스 전문 코드 (61:SMS요청, 63:인증번호확인)
    private final String otrnsWtid;

    @Setter private String mmtTccoDvdCd; // 통신사
    @Setter private String buyerNm;

    @Setter @Crypto(type = Crypto.Type.ENCRYPTION, algorithm = Crypto.Algorithm.SEED, cryptoKey = Crypto.CryptoKey.DB)
    private String ecdCphno;
    @Setter @Crypto(type = Crypto.Type.ENCRYPTION, algorithm = Crypto.Algorithm.SEED, cryptoKey = Crypto.CryptoKey.DB)
    private String ecdBthDt;


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
    public RecodeCellPhoneAuthTrns(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        this.wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        this.otrnsWtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        this.jobDvdCd = activityCellPhoneAuth.getJobCodes().getCode();
        this.idcDvdCd = activityCellPhoneAuth.getIdcDvdCd();
        this.jnoffcId = activityCellPhoneAuth.getJnoffcId();

        this.srlno = DataFunctions.makeSrlno.apply(new Date());

        final String[] datetime = DateFunctions.getDateAndTime.apply(new Date());
        this.regiDt = datetime[0];
        this.regiTm = datetime[1];

        if (JobCodes.JOB_CODE_18.getCode().equals(this.jobDvdCd)){
            this.authReqTypeCd = MobiliansMsgType.SEND_SMS.getCode();
            this.mmtTccoDvdCd = activityCellPhoneAuth.getSendSmsAuthNumb().getMmtTccoDvdCd();
            this.buyerNm = activityCellPhoneAuth.getSendSmsAuthNumb().getBuyerNm();
            this.ecdCphno = activityCellPhoneAuth.getSendSmsAuthNumb().getEcdCphno();
            this.ecdBthDt = activityCellPhoneAuth.getSendSmsAuthNumb().getUserSocNo();
        } else if (JobCodes.JOB_CODE_19.getCode().equals(this.jobDvdCd)) {
            this.authReqTypeCd = MobiliansMsgType.CERTIFICATION.getCode();
        } else { this.authReqTypeCd = "NL"; }
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

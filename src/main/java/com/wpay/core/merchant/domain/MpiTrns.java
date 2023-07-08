package com.wpay.core.merchant.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Getter
@ToString
@Entity
@Table(
        name="WPAY_MPI_TRNS",
        indexes = {
                @Index(name = "IA01_WPAY_MPI_TRNS", columnList = "REGI_DT"),
                @Index(name = "IA02_WPAY_MPI_TRNS", columnList = "OTRNS_WTID")
        }
)
@IdClass(MpiTrns.MpiTrnsPrimaryKey.class)
public class MpiTrns {

    @Id @Column(name="WTID", length=64, unique=true)
    private String wtid;

    @Id @Column(name="SRLNO", unique=true)
    private Long srlno;

    @Column(name="IDC_DVD_CD", length=50)
    private String idcDvdCd;

    @Column(name="JOB_DVD_CD", length=2)
    private String jobDvdCd;

    @Column(name="REGI_DT", length=8)
    private String regiDt;

    @Column(name="REGI_TM", length=6)
    private String regiTm;

    @Column(name="WPAY_USERID", length=100)
    private String wpayUserid;

    @Column(name="JNOFFC_ID", length=20)
    private String jnoffcId;

    @Column(name="JNOFFC_USER_KEY_VALUE")
    private String jnoffcUserKeyValue;

    @Column(name="JNOFFC_USERID", length=100)
    private String jnoffcUserid;

    @Column(name="AUTH_REQ_TYPE_CD", length=50)
    private String authReqTypeCd;

    @Column(name="MMT_TCCO_DVD_CD", length=3)
    private String mmtTccoDvdCd;

    @Column(name="BUYER_NM", length=100)
    private String buyerNm;

    @Column(name="ECD_CPHNO", length=200)
    private String ecdCphno;

    @Column(name="ECD_BTH_DT", length=100)
    private String ecdBthDt;

    @Column(name="GOODS_NM", length=100)
    private String goodsNm;

    @Column(name="BUYER_PRC")
    private Long buyPrc;

    @Column(name="MOBILE_TRANS_NO", length=18)
    private String mobileTransNo;

    @Column(name="CHNG_DT", length=8)
    private String chngDt;

    @Column(name="CHNG_TM", length=6)
    private String chngTm;

    @Column(name="PAY_RSLT_CD", length=6)
    private String payRsltCd;

    @Column(name="RSLT_MSG_CONTS", length=1000)
    private String rsltMsgConts;

    @Column(name="REPS_GRM_CONTS", length=1000)
    private String rspsGrmConts;

    @Column(name="CONN_URL", length=500)
    private String connUrl;

    @Column(name="OTRNS_WTID", length=64)
    private String otrnsWtid;

    @Data
    @Embeddable
    static class MpiTrnsPrimaryKey implements Serializable {
        private String wtid;
        private String srlno;
    }
}

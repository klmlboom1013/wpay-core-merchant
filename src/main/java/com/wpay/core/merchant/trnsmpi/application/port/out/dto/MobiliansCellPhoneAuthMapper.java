package com.wpay.core.merchant.trnsmpi.application.port.out.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class MobiliansCellPhoneAuthMapper extends BaseCellPhoneAuthMapper {

    String ciCode;
    String authToken;
    String mobileId;
    String infoOfferYn;

    @Builder
    public MobiliansCellPhoneAuthMapper(String wtid, String mid, String resultCode, String resultMsg, String ciCode, String authToken, String mobileId, String infoOfferYn) {
        super(wtid, mid, resultCode, resultMsg);
        this.ciCode = ciCode;
        this.authToken = authToken;
        this.mobileId = mobileId;
        this.infoOfferYn = infoOfferYn;
    }
}

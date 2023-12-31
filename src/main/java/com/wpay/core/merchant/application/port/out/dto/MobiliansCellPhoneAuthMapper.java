package com.wpay.core.merchant.application.port.out.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class MobiliansCellPhoneAuthMapper {
    private String resultCode;
    private String resultMsg;
    private String ciCode;
    private String authToken;
    private String mobileId;
    private String msgType;
    private String recvConts;
    private String connUrl;
}

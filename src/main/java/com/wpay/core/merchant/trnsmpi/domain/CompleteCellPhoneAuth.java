package com.wpay.core.merchant.trnsmpi.domain;

import lombok.*;

@Getter
@Value
@Builder
@ToString
@EqualsAndHashCode
public class CompleteCellPhoneAuth {
    String wtid;
    String mid;


}

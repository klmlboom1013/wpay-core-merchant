package com.wpay.core.merchant.domain;

import lombok.*;

@Getter
@Value
@Builder
@ToString
@EqualsAndHashCode
public class CompleteCellPhoneAuth {
    String wtid;
    String jnoffcId;
}

package com.wpay.core.merchant.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class MpiBasicInfoMapper {
    String wtid;
    String message;
}

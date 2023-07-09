package com.wpay.core.merchant.adapter.out.dto;

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
}

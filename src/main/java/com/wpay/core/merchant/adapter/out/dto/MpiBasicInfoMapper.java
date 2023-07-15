package com.wpay.core.merchant.adapter.out.dto;

import lombok.*;

@Value
@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MpiBasicInfoMapper {
    String wtid;
    String mid;
    String message;
}

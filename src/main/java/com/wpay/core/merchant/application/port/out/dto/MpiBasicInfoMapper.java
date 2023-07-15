package com.wpay.core.merchant.application.port.out.dto;

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

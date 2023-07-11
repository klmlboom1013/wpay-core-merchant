package com.wpay.core.merchant.domain;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.SearchMpiBasicInfoOption;
import lombok.*;


@Value
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MpiTrnsActivity {

    SearchMpiBasicInfoOption option;

    MpiTrnsId id;
    JobCode jobCode;
    String mid;

    @Builder
    @ToString
    public static class MpiTrnsId {
        @Getter
        private final String wtid;
        @Setter @Getter
        private Long srlno;
    }
}

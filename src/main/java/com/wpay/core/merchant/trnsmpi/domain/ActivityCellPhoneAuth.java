package com.wpay.core.merchant.trnsmpi.domain;

import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.enums.JobCodes;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityCellPhoneAuth {
    private final JobCodes jobCodes;
    private final String mid;
    private final String serverName;

    @Setter private MpiTrnsId mpiTrnsId;

    @Setter private SendSmsAuthNumb sendSmsAuthNumb;
    @Setter private SendVerifyAuthNumb sendVerifyAuthNumb;


    @Builder
    public ActivityCellPhoneAuth(JobCodes jobCodes, String mid, String wtid, String serverName) {
        this.jobCodes = jobCodes;
        this.mid = mid;
        this.mpiTrnsId = MpiTrnsId.builder().wtid(wtid).build();
        this.serverName = serverName;
    }

    /**
     * MpiTrns Key
     */
    @Value
    @Getter
    @Builder
    public static class MpiTrnsId {
        String wtid;
        Long srlno;
    }

    @Getter
    @Value
    @ToString
    @EqualsAndHashCode(callSuper = false)
    public static class SendSmsAuthNumb extends SelfValidating<SendSmsAuthNumb> {
        @NotBlank
        String userNm;
        @NotBlank
        String hCorp;
        @NotBlank
        String hNum;
        @NotBlank
        String birthDay;
        @NotBlank
        String socialNo2;

        String mobileId;

        @Builder
        public SendSmsAuthNumb(String userNm, String hCorp, String hNum, String birthDay, String socialNo2, String mobileId) {
            this.userNm = userNm;
            this.hCorp = hCorp;
            this.hNum = hNum;
            this.birthDay = birthDay;
            this.socialNo2 = socialNo2;
            this.mobileId = mobileId;
            this.validateSelf();
        }
    }

    @Getter
    @Value
    @ToString
    @EqualsAndHashCode(callSuper = false)
    public static class SendVerifyAuthNumb extends SelfValidating<SendSmsAuthNumb> {
        @NotBlank
        String authNumb;

        @Builder
        public SendVerifyAuthNumb(String authNumb) {
            this.authNumb = authNumb;
            this.validateSelf();
        }
    }
}

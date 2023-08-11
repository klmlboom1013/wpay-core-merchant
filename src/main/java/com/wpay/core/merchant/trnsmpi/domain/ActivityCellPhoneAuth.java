package com.wpay.core.merchant.trnsmpi.domain;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.exception.customs.JobCodeException;
import com.wpay.common.global.functions.PrivacyFunctions;
import com.wpay.core.merchant.global.enums.MobileCarrier;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthVerifyCommand;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;


@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityCellPhoneAuth {
    private final JobCodes jobCodes;
    private final String jnoffcId;
    private final String idcDvdCd;
    private final MpiTrnsId mpiTrnsId;

    @Setter private SendSmsAuthNumb sendSmsAuthNumb;
    @Setter private SendVerifyAuthNumb sendVerifyAuthNumb;

    /**
     * 모빌리언스 연동 결과
     */
    @Setter
    private ReceiveMobiliansCellPhoneAuth receiveMobiliansCellPhoneAuth;

    @Builder
    public ActivityCellPhoneAuth(BaseCommand<?> baseCommand) {
        this.jobCodes = baseCommand.getJobCodes();
        this.jnoffcId = baseCommand.getJnoffcId();
        this.idcDvdCd = baseCommand.getIdcDvdCd();
        this.mpiTrnsId = MpiTrnsId.builder().wtid(baseCommand.getWtid()).build();

        if(JobCodes.JOB_CODE_18.equals(this.jobCodes)) {
            this.sendSmsAuthNumb = SendSmsAuthNumb.builder().build();
            BeanUtils.copyProperties(baseCommand, this.sendSmsAuthNumb);
            this.sendSmsAuthNumb.init();
        } else if(JobCodes.JOB_CODE_19.equals(this.jobCodes)) {
            this.sendVerifyAuthNumb = SendVerifyAuthNumb.builder()
                    .authNumb(((CellPhoneAuthVerifyCommand) baseCommand).getAuthNumb())
                    .build();
        } else {
            throw new JobCodeException(this.mpiTrnsId.getWtid(), this.jnoffcId);
        }
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

    @Setter
    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode(callSuper = false)
    public static class SendSmsAuthNumb {
        private String buyerNm; /* 이름 */
        private String ecdCphno; /* 휴대폰 번호 */
        private String socialNo2; /* 주민번호 7번째 자리 (성별) */
        private String ecdBthDt; /* 6자리 또는 8자리 생년월일 */
        private String mobilTransNo; /* 통신사업자 ID (알뜰폰 인증번호 요청 시 필수) */
        private String mmtTccoDvdCd; /* 통신사 */

        private MobileCarrier mobileCarrier; /* 통신사 */

        private String userSocNo; /* 8자리 생년월일(YYYYMMDD) */
        private String gender; /* 성별 코드 */
        private String foreiner; /* 외국인 여부 */
        private String altteul; /* 알뜰폰 여부 */

        public void init() {
            this.mobileCarrier = MobileCarrier.getInstance(mmtTccoDvdCd);
            this.altteul = this.mobileCarrier.getAltteul();
            this.gender = PrivacyFunctions.findGenderCode.apply(Integer.parseInt(socialNo2));
            this.foreiner = PrivacyFunctions.findForeignerYN.apply(socialNo2);
            this.userSocNo = (ecdBthDt.length() == 8) ? ecdBthDt : PrivacyFunctions.findBirthdayFirstYY.apply(socialNo2) + ecdBthDt;
        }
    }


    @Getter
    @Value
    @Builder
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class SendVerifyAuthNumb {
        @NotBlank
        String authNumb;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @EqualsAndHashCode(callSuper = false)
    public static class ReceiveMobiliansCellPhoneAuth {
        private String resultCode;
        private String resultMsg;
        private String ciCode;
        private String authToken;
        private String mobileId;
        private String msgType;
        private String recvConts;
        private String connUrl;
    }
}

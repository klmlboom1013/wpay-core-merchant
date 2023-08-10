package com.wpay.core.merchant.trnsmpi.domain;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.exception.customs.JobCodeException;
import com.wpay.common.global.functions.PrivacyFunctions;
import com.wpay.core.merchant.global.enums.MobileCarrier;

import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthSmsCommand;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthVerifyCommand;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityCellPhoneAuth {
    private final JobCodes jobCodes;
    private final String jnoffcId;
    private final String idcDvdCd;
    private final MpiTrnsId mpiTrnsId;
    private final SendSmsAuthNumb sendSmsAuthNumb;
    private final SendVerifyAuthNumb sendVerifyAuthNumb;

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
            this.sendSmsAuthNumb = SendSmsAuthNumb.builder().cellPhoneAuthSmsCommand((CellPhoneAuthSmsCommand) baseCommand).build();
            this.sendVerifyAuthNumb = SendVerifyAuthNumb.builder().build();
        } else if(JobCodes.JOB_CODE_19.equals(this.jobCodes)) {
            this.sendVerifyAuthNumb = SendVerifyAuthNumb.builder().cellPhoneAuthVerifyCommand((CellPhoneAuthVerifyCommand) baseCommand).build();
            this.sendSmsAuthNumb = SendSmsAuthNumb.builder().build();
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

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = false)
    public static class SendSmsAuthNumb {
        private final String userNm; /* 이름 */
        private final MobileCarrier commandId; /* 통신사 */
        private final String phoneNo; /* 휴대폰 번호 */
        private final String userSocNo; /* 생년월일 YYYYMMDD */
        private final String gender; /* 성별 코드 */
        private final String foreiner; /* 외국인 여부 */
        private final String altteul; /* 알뜰폰 여부 */
        @Setter
        private String mobilId; /* 통신사업자 ID (알뜰폰 인증번호 요청 시 필수) */

        @Builder
        public SendSmsAuthNumb(@NonNull CellPhoneAuthSmsCommand cellPhoneAuthSmsCommand) {
            final String socialNo2 = cellPhoneAuthSmsCommand.getSocialNo2();
            final String birthDay = cellPhoneAuthSmsCommand.getBirthday();
            this.mobilId = cellPhoneAuthSmsCommand.getMobilId();
            this.userNm = cellPhoneAuthSmsCommand.getUserNm();
            this.commandId = MobileCarrier.getInstance(cellPhoneAuthSmsCommand.getHcorp());
            this.altteul = this.commandId.getAltteul();
            this.phoneNo = cellPhoneAuthSmsCommand.getHnum();
            this.gender = PrivacyFunctions.findGenderCode.apply(Integer.parseInt(socialNo2));
            this.foreiner = PrivacyFunctions.findForeignerYN.apply(socialNo2);
            this.userSocNo = (birthDay.length() == 8) ? birthDay : PrivacyFunctions.findBirthdayFirstYY.apply(socialNo2) + birthDay;
        }
    }

    @Getter
    @Value
    @ToString
    @EqualsAndHashCode(callSuper = false)
    public static class SendVerifyAuthNumb {
        @NotBlank
        String authNumb;

        @Builder
        public SendVerifyAuthNumb(@NonNull CellPhoneAuthVerifyCommand cellPhoneAuthVerifyCommand) {
            this.authNumb = cellPhoneAuthVerifyCommand.getAuthNumb();
        }
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

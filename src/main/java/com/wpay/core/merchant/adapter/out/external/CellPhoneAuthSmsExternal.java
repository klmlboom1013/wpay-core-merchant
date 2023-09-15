package com.wpay.core.merchant.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.core.merchant.application.port.out.external.CellPhoneAuthSmsExternalPort;
import com.wpay.core.merchant.application.port.out.external.CellPhoneAuthSmsExternalVersion;
import com.wpay.core.merchant.domain.ActivityCellPhoneAuth;
import com.wpay.core.merchant.global.enums.MobiliansMsgType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class CellPhoneAuthSmsExternal implements CellPhoneAuthSmsExternalPort {

    private static final String MOBILIANS_SMS_RESULT_SUCCESS_CODE = "0000";

    @Qualifier(value = "mobiliansWebClient")
    private final WebClient mobiliansWebClient;


    @Override
    public CellPhoneAuthSmsExternalVersion getVersionCode() { return CellPhoneAuthSmsExternalVersion.v1; }

    @Override
    public boolean sendConfirmMvnoCompanyRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String jnoffcId = activityCellPhoneAuth.getJnoffcId();
        log.info("[{}][{}] 모빌리언스 휴대폰 본인인증 통신사 MVNO 사업자 확인 요청 External 시작.", jnoffcId, wtid);

        // TODO 모빌리언스 MVNO 통신사 사업자 확인 요청 연동 구현 해 주세요.
        /*
         * 모빌리언스 응답 코드와 MOBILIANS_RESULT_SUCCESS_CODE 비교한 결과 리턴 하면 됩.
         * 응답 데이터 중 mobilId 받아 activityCellPhoneAuth.sendSmsAuthNumb 에 저장 한다.
         */
        activityCellPhoneAuth.getSendSmsAuthNumb().setMobilTransNo("sampleMobilId001");

        return true;
    }

    @Override
    public boolean sendSmsAuthNumbRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String jnoffcId = activityCellPhoneAuth.getJnoffcId();

        /* KTR, LGR 알뜰폰 사업자 확인 (SKR은 알뜰폰 사업자 확인 제외) */
        if ("Y".equals(activityCellPhoneAuth.getSendSmsAuthNumb().getAltteul()) &&
                Boolean.FALSE.equals(this.sendConfirmMvnoCompanyRun(activityCellPhoneAuth))) {
            log.error("[{}][{}] ", jnoffcId, wtid);
            throw new CustomException(ErrorCode.HTTP_STATUS_500, "모빌리언스 알뜰폰 사업자 번호 조회가 원활 하지않 습니다. 잠시 후 다시 시도 해 주세요.");
        }
        log.info("[{}][{}] 모빌리언스 휴대폰 본인인증 SMS 인증번호 발송 요청 External 시작.", jnoffcId, wtid);

        // TODO 모빌리언스 휴대폰 본인인증 인증번호 SMS 발송 요청 구현 해 주세요.
        /*
         * 모빌리언스 응답 코드와 MOBILIANS_RESULT_SUCCESS_CODE 비교한 결과 리턴 하면 됩.
         *  [example] return MOBILIANS_RESULT_SUCCESS_CODE.equals(mobilians.resultCode);
         */
        activityCellPhoneAuth.setReceiveMobiliansCellPhoneAuth(
                ActivityCellPhoneAuth.ReceiveMobiliansCellPhoneAuth.builder()
                        .mobileId(activityCellPhoneAuth.getSendSmsAuthNumb().getMobilTransNo())
                        .resultCode(MOBILIANS_SMS_RESULT_SUCCESS_CODE)
                        .resultMsg("")
                        .authToken("123456")
                        .recvConts("sample-sms-recv-message-conts")
                        .msgType(MobiliansMsgType.SEND_SMS.getCode())
                        .build());
        return true;
    }


}

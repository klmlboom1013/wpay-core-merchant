package com.wpay.core.merchant.trnsmpi.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.global.config.WebClientConfiguration;
import com.wpay.core.merchant.global.enums.MobiliansMsgType;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MobiliansCellPhoneAuthMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthVerifyExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthVerifyExternalVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class CellPhoneVerifyExternal implements CellPhoneAuthVerifyExternalPort {

    private static final String MOBILIANS_SMS_RESULT_SUCCESS_CODE = "0000";

    private final WebClientConfiguration webClientConfiguration;


    @Override
    public CellPhoneAuthVerifyExternalVersion getVersionCode() { return CellPhoneAuthVerifyExternalVersion.v1; }

    @Override
    public MobiliansCellPhoneAuthMapper sendVerifyAuthNumbRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 모빌리언스 휴대폰 본인인증 인증번호 확인 요청 External 시작.", mid, wtid);

        // 응답 데이터 Mapper 세팅.
        return MobiliansCellPhoneAuthMapper.builder()
                .resultCode(MOBILIANS_SMS_RESULT_SUCCESS_CODE)
                .resultMsg("")
                .recvConts("sample-sms-recv-message-conts")
                .msgType(MobiliansMsgType.CERTIFICATION.getCode())
                .build();
    }


}

package com.wpay.core.merchant.trnsmpi.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.global.config.WebClientConfiguration;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.CellPhoneAuthSmsMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthSmsExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthSmsExternalVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class CellPhoneAuthSmsExternal implements CellPhoneAuthSmsExternalPort {

    private final WebClientConfiguration webClientConfiguration;

//    @Value("${external.target.mpi.basic-info-url}")
    private String smsAuthNumbUrl;

    @Override public CellPhoneAuthSmsExternalVersion getVersionCode() { return CellPhoneAuthSmsExternalVersion.v1; }

    @Override
    public CellPhoneAuthSmsMapper sendSmsAuthNumbRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 모빌리언스 휴대폰 본인인증 SMS 인증번호 발송 요청 External 시작.", mid, wtid);

        // HTTP 통신 MPI 연동 정보 WebClient 세팅
//        final WebClient webClient = webClientConfiguration.webClient()
//                .mutate()
//                .baseUrl(smsAuthNumbUrl)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
//                .build();

        // Send & Receive MPI
//        final String result = webClient
//                .post() // HTTP 통신 메소드 GET 설정
//                .retrieve() // Sync 통신 설정
//                .bodyToMono(String.class) // 응답 받을 객체 String.class 설정.
//                .block(); // Send & Receive
//        log.info(">> Send Mobilians SmsAuthNumb Response : [{}]", result);

        // 응답 데이터 Mapper 세팅.
        return CellPhoneAuthSmsMapper.builder().build();
    }


}

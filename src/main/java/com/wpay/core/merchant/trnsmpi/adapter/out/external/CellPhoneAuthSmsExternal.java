package com.wpay.core.merchant.trnsmpi.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MobiliansCellPhoneAuthMapper;
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

    @Override
    public CellPhoneAuthSmsExternalVersion getVersionCode() { return CellPhoneAuthSmsExternalVersion.v1; }

    @Override
    public MobiliansCellPhoneAuthMapper sendConfirmMvnoCompanyRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 모빌리언스 휴대폰 본인인증 통신사 MVNO 사업자 확인 요청 External 시작.", mid, wtid);

        return MobiliansCellPhoneAuthMapper.builder()
                .wtid(wtid)
                .mid(mid)
                .resultCode("0000")
                .resultMsg("SUCCESS")
                .build();
    }

    @Override
    public MobiliansCellPhoneAuthMapper sendSmsAuthNumbRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 모빌리언스 휴대폰 본인인증 SMS 인증번호 발송 요청 External 시작.", mid, wtid);

        return MobiliansCellPhoneAuthMapper.builder()
                .wtid(wtid)
                .mid(mid)
                .resultCode("0000")
                .resultMsg("SUCCESS")
                .build();
    }


}

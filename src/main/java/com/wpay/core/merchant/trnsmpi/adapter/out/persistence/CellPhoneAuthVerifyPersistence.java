package com.wpay.core.merchant.trnsmpi.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthVerifyPersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthVerifyPersistenceVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
public class CellPhoneAuthVerifyPersistence implements CellPhoneAuthVerifyPersistencePort {
    @Override
    public CellPhoneAuthVerifyPersistenceVersion getVersionCode() {
        return CellPhoneAuthVerifyPersistenceVersion.v1;
    }

    @Override
    public void saveTrnsVerifyAuthNumbRun (ActivityCellPhoneAuth ActivityCellPhoneAuth) {
        final String wtid = ActivityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String jnoffcId = ActivityCellPhoneAuth.getJnoffcId();
        log.info("[{}][{}] 모빌리언스 연동 휴대폰 본인인증 인증번호 확인 Transaction DB 처리 시작.", jnoffcId, wtid);
    }
}

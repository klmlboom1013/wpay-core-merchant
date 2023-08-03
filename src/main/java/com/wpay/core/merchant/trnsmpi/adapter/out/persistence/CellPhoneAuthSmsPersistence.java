package com.wpay.core.merchant.trnsmpi.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthSmsPersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthSmsPersistenceVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
public class CellPhoneAuthSmsPersistence implements CellPhoneAuthSmsPersistencePort {

    private final MpiTrnsRepository mpiTrnsRepository;

    @Override
    public CellPhoneAuthSmsPersistenceVersion getVersionCode() {
        return CellPhoneAuthSmsPersistenceVersion.v1;
    }

    @Override
    public Integer countBySmsAuthNumb (ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 휴대폰 본인인증 SMS 인증번호 발송 요청 횟수 제한 검증 시작.", mid, wtid);

        final Integer count = this.mpiTrnsRepository.getCountByWtidAndJobDvdCd(wtid, this.getJobCode().getCode());
        log.info("[{}][{}] SMS 발송 요청 횟수 = {}", mid, wtid, count);

        return count;
    }

    @Override
    public void saveTrnsSmsAuthNumbRun (ActivityCellPhoneAuth activityCellPhoneAuth) {
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 모빌리언스 연동 휴대폰 본인인증 SMS 인증번호 발송 Transaction DB 처리 시작.", mid, wtid);
    }
}

package com.wpay.core.merchant.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.core.merchant.application.port.out.persistence.CellPhoneAuthSmsPersistencePort;
import com.wpay.core.merchant.application.port.out.persistence.CellPhoneAuthSmsPersistenceVersion;
import com.wpay.core.merchant.domain.RecodeCellPhoneAuthTrns;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;

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
    public Integer countBySmsAuthNumb (String wtid) {
        log.info("[{}] 휴대폰 본인인증 SMS 인증번호 발송 요청 횟수 제한 검증 시작.", wtid);
        final Integer count = this.mpiTrnsRepository.getCountByWtidAndJobDvdCd(wtid, this.getJobCode().getCode());
        log.info("[{}] SMS 발송 요청 횟수 = {}", wtid, count);
        return count;
    }

    @Override
    public void saveTrnsSmsAuthNumbRun (RecodeCellPhoneAuthTrns recodeCellPhoneAuthTrns) {
        final String wtid = recodeCellPhoneAuthTrns.getWtid();
        final String jnoffcId = recodeCellPhoneAuthTrns.getJnoffcId();
        log.info("[{}][{}] 모빌리언스 연동 휴대폰 본인인증 SMS 인증번호 발송 Transaction DB 처리 시작.\n[{}]",
                jnoffcId, wtid, recodeCellPhoneAuthTrns.toString());

        final MpiTrnsJpaEntity entity = new MpiTrnsJpaEntity();
        BeanUtils.copyProperties(recodeCellPhoneAuthTrns, entity);
        log.info("[{}][{}] Entity Save Set {}", jnoffcId, wtid, entity);

        final MpiTrnsJpaEntity resultEntity = this.mpiTrnsRepository.save(entity);
        log.info("[{}][{}] MPI_TRNS SAVE 완료: [{}]", jnoffcId, wtid, resultEntity.toString());
    }
}

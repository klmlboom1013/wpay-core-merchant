package com.wpay.core.merchant.trnsmpi.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.PortOutFactory;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.CellPhoneAuthUseCasePort;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.CellPhoneAuthVerifyUseCaseVersion;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthVerifyExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthVerifyExternalVersion;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthVerifyPersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthVerifyPersistenceVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;
import com.wpay.core.merchant.trnsmpi.domain.CompleteCellPhoneAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class CellPhoneAuthVerifyService implements CellPhoneAuthUseCasePort {

    private final PortOutFactory portOutFactory;

    @Override public JobCodes getJobCode() { return JobCodes.JOB_CODE_19; }
    @Override public CellPhoneAuthVerifyUseCaseVersion getVersionCode() { return CellPhoneAuthVerifyUseCaseVersion.v1; }

    @Override
    public BaseResponse sendMobiliansRun(ActivityCellPhoneAuth activityCellPhoneAuth){
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String jnoffcId = activityCellPhoneAuth.getJnoffcId();
        log.info("[{}][{}] 휴대폰 본인인증 인증번호 확인 요청 Service 시작.", jnoffcId, wtid);


        /* 모빌리언스 본인인증 인증번호 확인 요청 연동 */
        this.getExternal().sendVerifyAuthNumbRun(activityCellPhoneAuth);

        /* 모빌리언스 연동 이력 DB 저장 */
        this.getPersistence().saveTrnsVerifyAuthNumbRun(activityCellPhoneAuth);

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(CompleteCellPhoneAuth.builder().jnoffcId(jnoffcId).wtid(wtid).build())
                .build();
    }

    /**
     * Persistence
     */
    private CellPhoneAuthVerifyPersistencePort getPersistence() {
        return (CellPhoneAuthVerifyPersistencePort) this.portOutFactory.getPersistencePort(
                CellPhoneAuthVerifyPersistenceVersion.v1.toString(), this.getJobCode().toString());
    }

    /**
     * External
     */
    private CellPhoneAuthVerifyExternalPort getExternal() {
        return (CellPhoneAuthVerifyExternalPort)this.portOutFactory.getExternalPort(
                CellPhoneAuthVerifyExternalVersion.v1.toString(), this.getJobCode().toString());
    }
}

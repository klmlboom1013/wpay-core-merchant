package com.wpay.core.merchant.trnsmpi.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.dto.BaseNoDataResponse;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.PortOutFactory;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.CellPhoneAuthSmsUseCaseVersion;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.CellPhoneAuthUseCasePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.CellPhoneAuthSmsMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthSmsExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.CellPhoneAuthSmsExternalVersion;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthSmsPersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.persistence.CellPhoneAuthSmsPersistenceVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class CellPhoneAuthSmsService implements CellPhoneAuthUseCasePort {

    private final PortOutFactory portOutFactory;

    @Override public JobCodes getJobCode() { return JobCodes.JOB_CODE_18; }
    @Override public CellPhoneAuthSmsUseCaseVersion getVersionCode() { return CellPhoneAuthSmsUseCaseVersion.v1; }

    @Override
    public BaseNoDataResponse sendMobiliansRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth){
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 휴대폰 본인인증 SMS 인증번호 발송 요청 Service 시작.", mid, wtid);

        /* ExternalPort 가져 오기 */
        final CellPhoneAuthSmsExternalPort cellPhoneAuthSmsExternalPort =
                (CellPhoneAuthSmsExternalPort)this.portOutFactory.getExternalPort(
                        CellPhoneAuthSmsExternalVersion.v1.toString(), this.getJobCode().toString());

        /* 모빌리언스 본인인증 SMS 인증번호 발송 요청 연동 */
        final CellPhoneAuthSmsMapper cellPhoneAuthSmsMapper = cellPhoneAuthSmsExternalPort.sendSmsAuthNumbRun(activityCellPhoneAuth);
        log.info("[{}][{}] 휴대폰 본인인증 SMS 발송 결과: {}", mid, wtid, cellPhoneAuthSmsMapper.toString());

        /* PersistencePort 가져 오기 */
        final CellPhoneAuthSmsPersistencePort cellPhoneAuthSmsPersistencePort =
                (CellPhoneAuthSmsPersistencePort) this.portOutFactory.getPersistencePort(
                        CellPhoneAuthSmsPersistenceVersion.v1.toString(), this.getJobCode().toString());

        /* 모빌리언스 연동 이력 DB 저장 */
        cellPhoneAuthSmsPersistencePort.saveTrnsSmsAuthNumbRun(activityCellPhoneAuth);
        return BaseNoDataResponse.builder().httpStatus(HttpStatus.OK).build();
    }
}

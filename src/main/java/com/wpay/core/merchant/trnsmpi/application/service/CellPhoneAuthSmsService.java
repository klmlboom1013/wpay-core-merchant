package com.wpay.core.merchant.trnsmpi.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.dto.BaseNoDataResponse;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
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

    private final static Long LIMIT_SEND_SMS_AUTH_NUMB = 5L;
    private final PortOutFactory portOutFactory;

    @Override public JobCodes getJobCode() { return JobCodes.JOB_CODE_18; }
    @Override public CellPhoneAuthSmsUseCaseVersion getVersionCode() { return CellPhoneAuthSmsUseCaseVersion.v1; }

    @Override
    public BaseNoDataResponse sendMobiliansRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth){
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String mid = activityCellPhoneAuth.getMid();
        log.info("[{}][{}] 휴대폰 본인인증 SMS 인증번호 발송 요청 Service 시작.", mid, wtid);

        if(LIMIT_SEND_SMS_AUTH_NUMB <= this.getPersistence().countBySmsAuthNumb(activityCellPhoneAuth)){
            final StringBuilder sb = new StringBuilder()
                    .append("SMS 인증번호 발송 요청 제한 횟수는 ").append(LIMIT_SEND_SMS_AUTH_NUMB)
                    .append(" 회 까지 이며 제한 횟수를 초과 하였습니다.").append("잠시 후 다시 시도 부탁 드립니다.");
            throw new CustomException(ErrorCode.HTTP_STATUS_403, sb.toString());
        }

        /* KTR, LGR 알뜰폰 사업자 확인 (SKR 알뜰폰은 사업자 확인 제외) */


        /* 모빌리언스 본인인증 SMS 인증번호 발송 요청 연동 */
        final CellPhoneAuthSmsMapper cellPhoneAuthSmsMapper = this.getExternal().sendSmsAuthNumbRun(activityCellPhoneAuth);
        log.info("[{}][{}] 휴대폰 본인인증 SMS 발송 결과: {}", mid, wtid, cellPhoneAuthSmsMapper.toString());

        /* PersistencePort 가져 오기 */
        final CellPhoneAuthSmsPersistencePort cellPhoneAuthSmsPersistencePort =
                (CellPhoneAuthSmsPersistencePort) this.portOutFactory.getPersistencePort(
                        CellPhoneAuthSmsPersistenceVersion.v1.toString(), this.getJobCode().toString());

        /* 모빌리언스 연동 이력 DB 저장 */
        cellPhoneAuthSmsPersistencePort.saveTrnsSmsAuthNumbRun(activityCellPhoneAuth);
        return BaseNoDataResponse.builder().httpStatus(HttpStatus.OK).build();
    }

    /**
     * Persistence
     */
    private CellPhoneAuthSmsPersistencePort getPersistence() {
        return (CellPhoneAuthSmsPersistencePort) this.portOutFactory.getPersistencePort(
                CellPhoneAuthSmsPersistenceVersion.v1.toString(), this.getJobCode().toString());
    }

    /**
     * External
     */
    private CellPhoneAuthSmsExternalPort getExternal() {
        return (CellPhoneAuthSmsExternalPort)this.portOutFactory.getExternalPort(
                CellPhoneAuthSmsExternalVersion.v1.toString(), this.getJobCode().toString());
    }
}

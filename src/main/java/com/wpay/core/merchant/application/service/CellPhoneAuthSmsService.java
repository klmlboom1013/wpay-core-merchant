package com.wpay.core.merchant.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.dto.BaseNoDataResponse;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.port.PortOutFactory;
import com.wpay.core.merchant.global.exception.LimitSendSmsException;
import com.wpay.core.merchant.application.port.in.usecase.CellPhoneAuthSmsUseCaseVersion;
import com.wpay.core.merchant.application.port.in.usecase.CellPhoneAuthUseCasePort;
import com.wpay.core.merchant.application.port.out.external.CellPhoneAuthSmsExternalPort;
import com.wpay.core.merchant.application.port.out.external.CellPhoneAuthSmsExternalVersion;
import com.wpay.core.merchant.application.port.out.persistence.CellPhoneAuthSmsPersistencePort;
import com.wpay.core.merchant.application.port.out.persistence.CellPhoneAuthSmsPersistenceVersion;
import com.wpay.core.merchant.domain.ActivityCellPhoneAuth;
import com.wpay.core.merchant.domain.RecodeCellPhoneAuthTrns;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class CellPhoneAuthSmsService implements CellPhoneAuthUseCasePort {

    private final static Integer LIMIT_SEND_SMS_AUTH_NUMB = 5;
    private final PortOutFactory portOutFactory;

    @Override public JobCodes getJobCode() { return JobCodes.JOB_CODE_18; }
    @Override public CellPhoneAuthSmsUseCaseVersion getVersionCode() { return CellPhoneAuthSmsUseCaseVersion.v1; }

    @Override
    public BaseNoDataResponse sendMobiliansRun(@NonNull ActivityCellPhoneAuth activityCellPhoneAuth){
        final String wtid = activityCellPhoneAuth.getMpiTrnsId().getWtid();
        final String jnoffcId = activityCellPhoneAuth.getJnoffcId();
        log.info("[{}][{}] 휴대폰 본인인증 SMS 인증번호 발송 요청 Service 시작.", jnoffcId, wtid);

        final int sendSmsCount = this.getPersistence().countBySmsAuthNumb(wtid);
        if(LIMIT_SEND_SMS_AUTH_NUMB <= sendSmsCount) {throw new LimitSendSmsException(wtid, jnoffcId, LIMIT_SEND_SMS_AUTH_NUMB, sendSmsCount);}

        /* 휴대폰 본인인증 연동 트랜잭션 Activity 생성. */
        final RecodeCellPhoneAuthTrns recodeCellPhoneAuthTrns = RecodeCellPhoneAuthTrns.builder()
                .activityCellPhoneAuth(activityCellPhoneAuth).build();
        try {
            /* 모빌리언스 휴대폰 본인인증 SMS 발송 요청 */
            this.getExternal().sendSmsAuthNumbRun(activityCellPhoneAuth);
        } catch (CustomException e) {
            throw e;
        } catch (Exception ex) {
            throw new CustomException(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_500).e(ex).build());
        } finally {
            /* 휴대폰 본인인증 연동 트랜잭션 Activity 모빌리언스 연도 결과 세팅. */
            recodeCellPhoneAuthTrns.setResultMapper(activityCellPhoneAuth);
            /* 모빌리언스 연동 이력 DB 저장 */
            this.getPersistence().saveTrnsSmsAuthNumbRun(recodeCellPhoneAuthTrns);
        }

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

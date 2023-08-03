package com.wpay.core.merchant.trnsmpi.application.port.in.usecase;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.port.in.UseCasePort;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthSmsCommand;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthVerifyCommand;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthUseCasePort extends UseCasePort {

    @Override
    default Object execute(SelfValidating<?> selfValidating){
        final BaseCommand<?> baseCommand = (BaseCommand<?>) selfValidating;
        ActivityCellPhoneAuth activity = ActivityCellPhoneAuth.builder()
                .jobCodes(baseCommand.getJobCodes())
                .wtid(baseCommand.getWtid())
                .mid(baseCommand.getMid())
                .serverName(baseCommand.getServerName())
                .build();
        if (JobCodes.JOB_CODE_18.equals(baseCommand.getJobCodes().getCode())) {
            final CellPhoneAuthSmsCommand cellPhoneAuthSmsCommand = (CellPhoneAuthSmsCommand) baseCommand;
            activity.setSendSmsAuthNumb(ActivityCellPhoneAuth.SendSmsAuthNumb.builder()
                    .birthDay(cellPhoneAuthSmsCommand.getBirthday())
                    .hCorp(cellPhoneAuthSmsCommand.getHcorp())
                    .hNum(cellPhoneAuthSmsCommand.getHnum())
                    .socialNo2(cellPhoneAuthSmsCommand.getSocialNo2())
                    .userNm(cellPhoneAuthSmsCommand.getUserNm())
                    .mobileId(cellPhoneAuthSmsCommand.getMobileId())
                    .build());
        } else if (JobCodes.JOB_CODE_19.equals(baseCommand.getJobCodes().getCode())) {
            final CellPhoneAuthVerifyCommand cellPhoneAuthVerifyCommand = (CellPhoneAuthVerifyCommand) baseCommand;
            activity.setSendVerifyAuthNumb(ActivityCellPhoneAuth.SendVerifyAuthNumb.builder()
                    .authNumb(cellPhoneAuthVerifyCommand.getAuthNumb())
                    .build());
        } else throw new CustomException(ErrorCode.HTTP_STATUS_500, "CommandDto - MobiliansJobCode 오류 입니다.");
        return this.sendMobiliansRun(activity);
    }

    Object sendMobiliansRun(ActivityCellPhoneAuth activityCellPhoneAuth);
}

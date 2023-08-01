package com.wpay.core.merchant.trnsmpi.adapter.in.web;

import com.wpay.common.global.annotation.WebAdapter;
import com.wpay.common.global.port.PortInFactory;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthSmsCommand;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthVerifyCommand;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.CellPhoneAuthSmsUseCaseVersion;
import com.wpay.core.merchant.trnsmpi.application.port.in.usecase.CellPhoneAuthVerifyUseCaseVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@WebAdapter
@RestController
@RequiredArgsConstructor
public class CellPhoneAuthController {

    private final PortInFactory portInFactory;

    /**
     * 휴대폰 본인 인증 - SMS 인증 번호 발송 요청
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/mobilians/{version}/sms-auth-numb")
    public ResponseEntity<?> smsAuthNumbRun (@PathVariable String version,
                                             @RequestBody CellPhoneAuthSmsCommand cellPhoneAuthSmsCommand) {
        return ResponseEntity.ok().body(
                portInFactory.getUseCasePort(
                        CellPhoneAuthSmsUseCaseVersion.getInstance(version).name(),
                        cellPhoneAuthSmsCommand.getJobCodes().toString())
                        .execute(cellPhoneAuthSmsCommand));

    }

    /**
     * 휴대폰 본인 인증 - 인증 번호 확인 요청
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/mobilians/{version}/verify-auth-numb")
    public ResponseEntity<?> verifyAuthNumbRun (@PathVariable String version,
                                                @RequestBody CellPhoneAuthVerifyCommand cellPhoneAuthVerifyCommand) {
        return ResponseEntity.ok().body(
                portInFactory.getUseCasePort(
                        CellPhoneAuthVerifyUseCaseVersion.getInstance(version).name(),
                        cellPhoneAuthVerifyCommand.getJobCodes().toString())
                        .execute(cellPhoneAuthVerifyCommand));
    }
}

package com.wpay.core.merchant.application.port.in.dto;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.core.merchant.application.port.in.usecase.CellPhoneAuthSmsUseCaseVersion;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CellPhoneAuthVerifyCommand extends BaseCommand<CellPhoneAuthVerifyCommand> {

    @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES, cryptoKey = Crypto.CryptoKey.API)
    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(min = 6, max = 6, message = "길이는 6 자리 입니다.")
    @Pattern(regexp = "[0-9]+", message = "숫자만 입력 가능 합니다.")
    private String authNumb;

    @Override
    public void validateSelf() {
        super.validateSelf();
    }

    @Override
    public void checkVersion(String version) {
        CellPhoneAuthSmsUseCaseVersion.getInstance(version);
    }

    @Override
    public JobCodes getJobCodes() {
        return JobCodes.JOB_CODE_19;
    }
}

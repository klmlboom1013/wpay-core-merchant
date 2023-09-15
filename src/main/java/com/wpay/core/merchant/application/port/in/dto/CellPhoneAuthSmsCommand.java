package com.wpay.core.merchant.application.port.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CellPhoneAuthSmsCommand extends BaseCommand<CellPhoneAuthSmsCommand> {

    /** 이름 */
    @JsonProperty("userNm")
    @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES, cryptoKey = Crypto.CryptoKey.API)
    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(max = 30, message = "길이는 최대 30 자리 입니다.")
    @Pattern(regexp = "[a-zA-Z가-힣]+", message = "영어 또는 한글만 입력 가능 합니다.")
    private String buyerNm;

    /** 통신사 */
    @JsonProperty("hcorp")
    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(min = 3, max = 3, message = "길이는 3 자리 입니다.")
    private String mmtTccoDvdCd;

    /** 폰 번호 */
    @JsonProperty("hnum")
    @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES, cryptoKey = Crypto.CryptoKey.API)
    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(min = 10, max = 11, message = "길이는 10~11 입니다.")
    @Pattern(regexp = "[0-9]+", message = "숫자만 입력 가능 합니다.")
    private String ecdCphno;

    /** 생년월일 */
    @JsonProperty("birthday")
    @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES, cryptoKey = Crypto.CryptoKey.API)
    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(min = 8, max = 8, message = "길이는 8자리 입니다.")
    @Pattern(regexp = "[0-9]+", message = "숫자만 입력 가능 합니다.")
    private String ecdBthDt;

    /** 주민등록번호 7 번째 자리 - 성별 값 */
    @Crypto(type = Crypto.Type.DECRYPTION, algorithm = Crypto.Algorithm.AES, cryptoKey = Crypto.CryptoKey.API)
    @NotBlank(message = "값이 Null이 될 수 없습니다.")
    @Size(max = 1, message = "길이는 1 자리 입니다.")
    @Pattern(regexp = "[0-9]+", message = "숫자만 입력 가능 합니다.")
    private String socialNo2;

    /** 모빌리언스 측 SMS 발송 재요청 구분 값 */
    @JsonProperty("mobilId")
    private String mobilTransNo;
    
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
        return JobCodes.JOB_CODE_18;
    }
}
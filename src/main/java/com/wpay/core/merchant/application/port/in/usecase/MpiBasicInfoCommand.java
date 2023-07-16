package com.wpay.core.merchant.application.port.in.usecase;

import com.wpay.core.merchant.global.dto.SelfValidating;
import com.wpay.core.merchant.global.enums.JobCode;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MpiBasicInfoCommand extends SelfValidating<MpiBasicInfoCommand> {

    public static final JobCode jobCode = JobCode.JOB_CODE_20;

    @NotNull(message = "값이 Null이 될 수 없습니다.")
    @Size(min=10, max=20, message = "길이는 10 부터 20 까지 입니다.")
    private String mid;

    @NotNull(message = "값이 Null이 될 수 없습니다.")
    @Size(max = 64, message = "길이가 64를 초과 할 수 없습니다.")
    private String wtid;

    @Setter
    private String serverName;

    @Override
    public void validateSelf() {
        super.validateSelf();
    }
}

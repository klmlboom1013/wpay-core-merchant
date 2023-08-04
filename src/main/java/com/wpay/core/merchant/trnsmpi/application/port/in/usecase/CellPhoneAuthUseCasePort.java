package com.wpay.core.merchant.trnsmpi.application.port.in.usecase;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.port.in.UseCasePort;
import com.wpay.core.merchant.trnsmpi.domain.ActivityCellPhoneAuth;

public interface CellPhoneAuthUseCasePort extends UseCasePort {

    @Override
    default Object execute(SelfValidating<?> selfValidating){
        return this.sendMobiliansRun(ActivityCellPhoneAuth.builder()
                .baseCommand((BaseCommand<?>) selfValidating)
                .build());
    }

    Object sendMobiliansRun(ActivityCellPhoneAuth activityCellPhoneAuth);
}

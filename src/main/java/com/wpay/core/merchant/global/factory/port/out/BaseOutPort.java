package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoVersion;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BaseOutPort {
    JobCode getJobCode();

    MpiBasicInfoVersion getVersionCode();

    PortDvdCode getPortDvdCode();
}

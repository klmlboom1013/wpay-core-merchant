package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.enums.MpiBasicInfoJobCode;
import com.wpay.core.merchant.enums.MpiBasicInfoVersion;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BaseOutPort {
    MpiBasicInfoJobCode getJobCode();

    MpiBasicInfoVersion getVersionCode();

    PortDvdCode getPortDvdCode();
}

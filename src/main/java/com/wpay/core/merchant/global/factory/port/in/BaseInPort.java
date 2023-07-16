package com.wpay.core.merchant.global.factory.port.in;

import com.wpay.core.merchant.enums.MpiBasicInfoJobCode;
import com.wpay.core.merchant.enums.MpiBasicInfoVersion;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BaseInPort {
    MpiBasicInfoJobCode getJobCode();

    MpiBasicInfoVersion getVersionCode();

    PortDvdCode getPortDvdCode();
}

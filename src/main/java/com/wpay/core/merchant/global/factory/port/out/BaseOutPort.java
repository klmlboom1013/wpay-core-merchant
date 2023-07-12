package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BaseOutPort {
    JobCode getJobCode();

    VersionCode getVersionCode();

    PortDvdCode getPortDvdCode();
}

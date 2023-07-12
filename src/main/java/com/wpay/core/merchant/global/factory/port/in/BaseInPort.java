package com.wpay.core.merchant.global.factory.port.in;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BaseInPort {
    JobCode getJobCode();

    VersionCode getVersionCode();

    PortDvdCode getPortDvdCode();
}

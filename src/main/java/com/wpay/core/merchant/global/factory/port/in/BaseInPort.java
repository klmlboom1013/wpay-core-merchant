package com.wpay.core.merchant.global.factory.port.in;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;

public interface BaseInPort {
    JobCode getJobCode();

    VersionCode getVersionCode();

    PortInDvdCode getPortInDvdCode();
}

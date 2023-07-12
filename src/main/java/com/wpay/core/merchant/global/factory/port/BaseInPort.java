package com.wpay.core.merchant.global.factory.port;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;

public interface BaseInPort {
    JobCode getJobCode();

    VersionCode getVersionCode();

    PortDvdCode getPortDvdCode();
}

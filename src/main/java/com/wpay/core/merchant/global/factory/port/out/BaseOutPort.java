package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;

public interface BaseOutPort {
    JobCode getJobCode();

    VersionCode getVersionCode();

    PortOutDvdCode getPortOutDvdCode();
}

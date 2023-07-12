package com.wpay.core.merchant.adapter.out.external;

import com.wpay.core.merchant.application.port.out.external.MpiBasicInfoExternal;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.global.annotation.ExternalAdapter;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.VersionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class MpiExternalAdepter implements MpiBasicInfoExternal {
    @Override
    public JobCode getJobCode() {
        return null;
    }

    @Override
    public VersionCode getVersionCode() {
        return null;
    }

    @Override
    public MpiBasicInfo sendMpiBasicInfo(ActivityMpiBasicInfo activityMpiBasicInfo) {
        return null;
    }
}

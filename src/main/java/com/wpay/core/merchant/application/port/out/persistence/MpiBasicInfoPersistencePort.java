package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.PersistencePort;
import com.wpay.core.merchant.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.RecodeMpiBasicInfoTrns;

public interface MpiBasicInfoPersistencePort extends PersistencePort {

    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_20; }

    MpiBasicInfoMapper loadActivitiesRun (ActivityMpiBasicInfo activityMpiBasicInfo);

    void recodeActivitiesRun(RecodeMpiBasicInfoTrns recodeMpiBasicInfoTrns);
}

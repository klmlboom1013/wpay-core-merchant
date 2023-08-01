package com.wpay.core.merchant.trnsmpi.application.port.out.persistence;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.PersistencePort;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiTrns;

public interface MpiBasicInfoPersistencePort extends PersistencePort {

    @Override default JobCodes getJobCode() { return JobCodes.JOB_CODE_20; }

    MpiBasicInfoMapper loadActivitiesRun (ActivityMpiTrns activityMpiTrns);

    void recodeActivitiesRun(ActivityMpiTrns activityMpiTrns);
}
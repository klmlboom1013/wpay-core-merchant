package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.factory.port.out.PersistencePort;
import com.wpay.core.merchant.domain.ActivityMpiTrns;
import com.wpay.core.merchant.domain.MpiBasicInfo;

public interface MpiBasicInfoPersistencePort extends PersistencePort {

    @Override default JobCode getJobCode() { return JobCode.JOB_CODE_20; }

    MpiBasicInfo loadActivitiesRun (ActivityMpiTrns activityMpiTrns);

    void recodeActivitiesRun(ActivityMpiTrns activityMpiTrns);
}

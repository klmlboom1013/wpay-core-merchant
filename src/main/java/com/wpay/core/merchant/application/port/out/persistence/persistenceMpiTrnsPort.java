package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.core.merchant.adapter.out.persistence.MpiTrnsJpaEntity;
import com.wpay.core.merchant.global.factory.port.out.BasePersistencePort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfo;

public interface persistenceMpiTrnsPort extends BasePersistencePort {
    @Override
    default MpiBasicInfo loadActivitiesRun (Object... args){
        return this.searchMpiTrnMpiBasicInfo((ActivityMpiBasicInfo)args[0]);
    }

    @Override
    default void recodeActivitiesRun(Object... args) {
        this.saveMpiTrnMpiBasicInfo((MpiTrnsJpaEntity) args[0]);
    }

    MpiBasicInfo searchMpiTrnMpiBasicInfo (ActivityMpiBasicInfo activityMpiBasicInfo);

    void saveMpiTrnMpiBasicInfo(MpiTrnsJpaEntity mpiTrnsJpaEntity);
}

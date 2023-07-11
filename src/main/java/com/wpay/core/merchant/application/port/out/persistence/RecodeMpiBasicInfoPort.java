package com.wpay.core.merchant.application.port.out.persistence;

import com.wpay.core.merchant.adapter.out.persistence.MpiTrnsJpaEntity;

public interface RecodeMpiBasicInfoPort {

    void recodeActivitiesRun(MpiTrnsJpaEntity mpiTrnsJpaEntity);
}

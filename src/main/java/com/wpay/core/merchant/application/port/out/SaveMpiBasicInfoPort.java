package com.wpay.core.merchant.application.port.out;

import com.wpay.core.merchant.adapter.out.persistence.entity.MpiTrns;

public interface SaveMpiBasicInfoPort {

    void saveActivities(MpiTrns mpiTrns);
}

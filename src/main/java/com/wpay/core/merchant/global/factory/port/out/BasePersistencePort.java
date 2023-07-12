package com.wpay.core.merchant.global.factory.port.out;

public interface BasePersistencePort extends BaseOutPort {

    Object loadActivitiesRun (Object... args);
    void recodeActivitiesRun(Object... args);

    default PortOutDvdCode getPortOutDvdCode() {
        return PortOutDvdCode.persistence;
    }
}

package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.factory.port.BaseOutPort;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BasePersistencePort extends BaseOutPort {

    Object loadActivitiesRun (Object... args);
    void recodeActivitiesRun(Object... args);

    @Override
    default PortDvdCode getPortDvdCode() {
        return PortDvdCode.persistence;
    }
}

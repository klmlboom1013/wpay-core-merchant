package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface PersistencePort extends BaseOutPort {
    @Override default PortDvdCode getPortDvdCode() {
        return PortDvdCode.persistence;
    }
}

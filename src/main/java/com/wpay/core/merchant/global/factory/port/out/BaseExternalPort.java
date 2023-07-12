package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.factory.port.BaseOutPort;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BaseExternalPort extends BaseOutPort {
    @Override
    default PortDvdCode getPortDvdCode() {
        return PortDvdCode.external;
    }
}

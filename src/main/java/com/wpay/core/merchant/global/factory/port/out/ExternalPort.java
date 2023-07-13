package com.wpay.core.merchant.global.factory.port.out;

import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface ExternalPort extends BaseOutPort {
    @Override default PortDvdCode getPortDvdCode() { return PortDvdCode.external; }
}

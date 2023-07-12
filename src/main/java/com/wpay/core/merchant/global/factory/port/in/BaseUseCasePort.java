package com.wpay.core.merchant.global.factory.port.in;

import com.wpay.core.merchant.global.dto.SelfValidating;
import com.wpay.core.merchant.global.factory.port.BaseInPort;
import com.wpay.core.merchant.global.factory.port.PortDvdCode;

public interface BaseUseCasePort extends BaseInPort {

    @Override default PortDvdCode getPortDvdCode() { return PortDvdCode.usecase; }

    Object execute(SelfValidating<?> selfValidating);
}

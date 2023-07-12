package com.wpay.core.merchant.global.factory.port.in;

import com.wpay.core.merchant.global.dto.SelfValidating;

public interface BaseUseCasePort extends BaseInPort {

    @Override default PortInDvdCode getPortInDvdCode() { return PortInDvdCode.useCase; }

    Object execute(SelfValidating<?> selfValidating);
}

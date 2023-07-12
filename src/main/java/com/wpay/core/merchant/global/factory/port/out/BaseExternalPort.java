package com.wpay.core.merchant.global.factory.port.out;

public interface BaseExternalPort extends BaseOutPort {
    default PortOutDvdCode getPortOutDvdCode() {
        return PortOutDvdCode.external;
    }
}

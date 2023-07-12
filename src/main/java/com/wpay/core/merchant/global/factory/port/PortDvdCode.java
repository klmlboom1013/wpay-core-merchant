package com.wpay.core.merchant.global.factory.port;

public enum PortDvdCode {
    persistence, external, usecase;

    public boolean equals(PortDvdCode code){
        return this.name().equals(code.name());
    }
}

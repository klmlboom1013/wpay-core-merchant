package com.wpay.core.merchant.global.cfgclient;

import com.wpay.common.global.annotation.ConfigClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Getter
@RefreshScope
@ConfigClient
public class EcomerceEnv {
    @Value("${token.expiration}")
    private String tokenExpirationTime;
    @Value("${token.secret}")
    private String tokenSecret;
    @Value("${gateway.ip}")
    private String gatewayIp;
}

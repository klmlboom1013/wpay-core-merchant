package com.wpay.core.merchant.global.cfgclient;

import com.wpay.common.global.annotation.ConfigClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Getter
@RefreshScope
@ConfigClient
public class ResourceEnv {
    @Value("${external.target.mpi.active}")
    private String mpiActive;
    @Value("${external.target.mpi.url}")
    private String mpiUrl;
}

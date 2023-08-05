package com.wpay.core.merchant.global.infra;

import com.wpay.common.global.infra.WebClientConfigure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientMerchantBeans {

    /**
     * MPI 통신 WebClient
     */
    @Bean(value = "mpiWebClient")
    public WebClient mpiWebClient() {
        return WebClientConfigure.builder()
                .timeoutConfigureSetUp(WebClientConfigure.TimeoutConfigureSetUp.builder()
                        .connectionTimeoutSeconds(3) //커넥션 타임아웃 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.)
                        .readTimeoutSeconds(2) //서버 응답 대기 시간 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.)
                        .writeTimeoutSeconds(2) //서버로 데이터를 보내는 시간 타임아웃 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.)
                        .build())
                .connectionPoolConfigureSetUp(WebClientConfigure.ConnectionPoolConfigureSetUp.builder()
                        .maxConnections(50) // connection pool의 갯수
                        .pendingAcquireTimeoutMills(0L) // 커넥션 풀에서 커넥션을 얻기 위해 기다리는 최대 시간 밀리초 [min:0 no wait]
                        .pendingAcquireMaxCount(1) // 커넥션 풀에서 커넥션을 가져오는 시도 횟수 (-1: no limit)
                        .maxIdleTimeMillis(2000L) // 커넥션 풀에서 idle 상태의 커넥션을 유지하는 시간 밀리초 [예: 2000L]
                        .build())
                .maxMemorySizeByteCount(1)
                .build().getWebClient();
    }

    /**
     * 모빌리언스 통신 WebClient
     */
    @Bean(value = "mobiliansWebClient")
    public WebClient mobiliansWebClient() {
        return WebClientConfigure.builder()
                .timeoutConfigureSetUp(WebClientConfigure.TimeoutConfigureSetUp.builder()
                        .connectionTimeoutSeconds(3) //커넥션 타임아웃 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.)
                        .readTimeoutSeconds(2) //서버 응답 대기 시간 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.)
                        .writeTimeoutSeconds(2) //서버로 데이터를 보내는 시간 타임아웃 max 10초 (10 이상 세팅할 경우 Build 시 10으로 강제 세팅 함.)
                        .build())
                .connectionPoolConfigureSetUp(WebClientConfigure.ConnectionPoolConfigureSetUp.builder()
                        .maxConnections(20) // connection pool의 갯수
                        .pendingAcquireTimeoutMills(0L) // 커넥션 풀에서 커넥션을 얻기 위해 기다리는 최대 시간 밀리초 [min:0 no wait]
                        .pendingAcquireMaxCount(1) // 커넥션 풀에서 커넥션을 가져오는 시도 횟수 (-1: no limit)
                        .maxIdleTimeMillis(2000L) // 커넥션 풀에서 idle 상태의 커넥션을 유지하는 시간 밀리초 [예: 2000L]
                        .build())
                .maxMemorySizeByteCount(1)
                .build().getWebClient();
    }
}

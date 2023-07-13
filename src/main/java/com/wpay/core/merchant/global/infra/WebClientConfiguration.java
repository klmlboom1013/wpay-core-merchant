package com.wpay.core.merchant.global.infra;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.ws.rs.core.MediaType;

@Log4j2
@Configuration
public class WebClientConfiguration {

    @Value("${external.mpi.basic-info-url}")
    private String mpiBasicInfoUrl;



    /**
     * MPI 기준 정보 조회 연동
     */
    @Bean
    public WebClient webClientForSendMpiBasicInfo() {
        log.info("mpiBasicInfoUrl: {}", mpiBasicInfoUrl);
        return WebClient.builder()
                .baseUrl(mpiBasicInfoUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 *1024))
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().tcpConfiguration(
                                client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                                        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10))
                                                .addHandlerLast(new WriteTimeoutHandler(10))))

                )).build();
    }
}

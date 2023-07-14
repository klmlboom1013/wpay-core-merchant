package com.wpay.core.merchant.global.infra;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Log4j2
@Configuration
public class WebClientConfiguration {

    @Value("${external.source.connection-timeout-mills}")
    private Integer connectionTimeoutMills;
    @Value("${external.source.read-timeout-sec}")
    private Integer readTimeoutSec;
    @Value("${external.source.write-timeout-sec}")
    private Integer writeTimeoutSec;

    @Bean
    public WebClient webClient() {
        log.debug("WebClient.builder method set connectionTimeoutMills: {}", connectionTimeoutMills);
        log.debug("WebClient.builder method set readTimeoutSec: {}", readTimeoutSec);
        log.debug("WebClient.builder method set writeTimeoutSec: {}", writeTimeoutSec);

        final HttpClient httpClient = HttpClient.create().secure().tcpConfiguration(
                client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMills)
                        .doOnConnected(conn ->
                                conn.addHandlerLast(new ReadTimeoutHandler(readTimeoutSec))
                                        .addHandlerLast(new WriteTimeoutHandler(writeTimeoutSec))));

        final ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);

        final ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2*1024*1024)) // 응답 Memory Buffer size 설정 2M (default: 256K)
                .build();

        exchangeStrategies.messageWriters().stream().filter(
                LoggingCodecSupport.class::isInstance).forEach(
                writer -> ((LoggingCodecSupport)writer)
                        .setEnableLoggingRequestDetails(true));

        return WebClient.builder()
                .clientConnector(clientHttpConnector)
                .exchangeStrategies(exchangeStrategies)
                .filter(
                        ExchangeFilterFunction.ofRequestProcessor(
                                clientRequest ->
                                {
                                    log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                                    clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                                    return Mono.just(clientRequest);
                                }))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                        clientResponse ->
                        {
                            clientResponse.headers().asHttpHeaders().forEach(
                                    (name, values) -> values.forEach(
                                            value -> log.debug("{} : {}", name, value)));
                            return Mono.just(clientResponse);
                        }))
                .build();
    }
}

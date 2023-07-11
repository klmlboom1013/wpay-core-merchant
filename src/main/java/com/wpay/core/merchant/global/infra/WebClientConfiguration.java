package com.wpay.core.merchant.global.infra;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.ws.rs.core.MediaType;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClientForSearchMpiBasicInfo(){
        return WebClient.builder()
                .baseUrl("http://relay.inicis.com/iniwallet60/INIwallet60.jsp")
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

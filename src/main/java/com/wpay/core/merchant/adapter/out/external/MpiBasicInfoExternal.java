package com.wpay.core.merchant.adapter.out.external;

import com.wpay.core.merchant.application.port.out.external.MpiBasicInfoExternalPort;
import com.wpay.core.merchant.domain.ActivityMpiTrns;
import com.wpay.core.merchant.domain.MpiBasicInfo;
import com.wpay.core.merchant.global.annotation.ExternalAdapter;
import com.wpay.core.merchant.global.enums.VersionCode;
import com.wpay.core.merchant.global.infra.WebClientConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.core.MediaType;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class MpiBasicInfoExternal implements MpiBasicInfoExternalPort {

    private final WebClientConfiguration webClientConfiguration;

    @Value("${external.target.mpi.basic-info-url}")
    private String mpiBasicInfoUrl;

    @Override public VersionCode getVersionCode() { return VersionCode.v1; }

    @Override
    public MpiBasicInfo sendMpiBasicInfoRun(ActivityMpiTrns activityMpiTrns) {

        final WebClient webClient = webClientConfiguration.webClient()
                .mutate()
                .baseUrl(mpiBasicInfoUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                .build();

        final String result = webClient.get()
                .uri("?mid={mid}&ch={ch}", activityMpiTrns.getMid(), "WPAY")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info(">> Send MPI Response : [{}]", result);

        return null;
    }
}

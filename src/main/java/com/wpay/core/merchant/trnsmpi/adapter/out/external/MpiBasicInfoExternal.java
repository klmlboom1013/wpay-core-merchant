package com.wpay.core.merchant.trnsmpi.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.global.config.WebClientConfiguration;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.MpiBasicInfoExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.MpiBasicInfoExternalVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiTrns;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.core.MediaType;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
class MpiBasicInfoExternal implements MpiBasicInfoExternalPort {

    private final WebClientConfiguration webClientConfiguration;

    @Value("${external.target.mpi.basic-info-url}")
    private String mpiBasicInfoUrl;

    @Override public MpiBasicInfoExternalVersion getVersionCode() { return MpiBasicInfoExternalVersion.v1; }

    @Override
    public MpiBasicInfoMapper sendMpiBasicInfoRun(@NonNull ActivityMpiTrns activityMpiTrns) {

        // HTTP 통신 MPI 연동 정보 WebClient 세팅
        final WebClient webClient = webClientConfiguration.webClient()
                .mutate()
                .baseUrl(mpiBasicInfoUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                .build();

        final String uriParam = new StringBuilder()
                .append("?mid=").append(activityMpiTrns.getMid())
                .append("&ch=WPAY")
                .toString();

        // Send & Receive MPI
        final String result = webClient
                .get() // HTTP 통신 메소드 GET 설정
                .uri(uriParam) // GET 방식 Request Param
                .retrieve() // Sync 통신 설정
                .bodyToMono(String.class) // 응답 받을 객체 String.class 설정.
                .block(); // Send & Receive
        log.info(">> Send MPI Response : [{}]", result);

        // 응답 데이터 Mapper 세팅.
        return MpiBasicInfoMapper.builder()
                .wtid(activityMpiTrns.getMpiTrnsId().getWtid())
                .mid(activityMpiTrns.getMid())
                .message(result)
                .url(new StringBuilder(mpiBasicInfoUrl).append(uriParam).toString())
                .build();
    }
}

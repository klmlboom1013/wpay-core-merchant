package com.wpay.core.merchant.trnsmpi.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.global.exception.BadWebClientRequestException;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.MpiBasicInfoExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.MpiBasicInfoExternalVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiBasicInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.MediaType;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
class MpiBasicInfoExternal implements MpiBasicInfoExternalPort {

    @Qualifier(value = "mpiWebClient")
    private final WebClient mpiWebClient;

    @Value("${external.target.mpi.basic-info-url}")
    private String mpiBasicInfoUrl;

    @Override public MpiBasicInfoExternalVersion getVersionCode() { return MpiBasicInfoExternalVersion.v1; }

    @Override
    public MpiBasicInfoMapper sendMpiBasicInfoRun(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {

        final String mpiUrl = new StringBuilder(mpiBasicInfoUrl).append("?")
                .append("mid=").append(activityMpiBasicInfo.getMid()).append("&")
                .append("ch=WPAY").toString();

        // Send & Receive MPI
        final String result = this.mpiWebClient
                .get() // HTTP 통신 메소드 GET 설정
                .uri(mpiUrl) // GET 방식 Request Param
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve() // Sync 통신 설정
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(
                        new BadWebClientRequestException(response.rawStatusCode(),
                                String.format("4xx MPI 요청 오류: statusCode: %s, response: %s, header: %s",
                                        response.rawStatusCode(),
                                        response.bodyToMono(String.class),
                                        response.headers().asHttpHeaders())
                        )))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(
                        new BadWebClientRequestException(response.rawStatusCode(),
                                String.format("5xx MPI 시스템 오류: statusCode: %s, response: %s, header: %s",
                                        response.rawStatusCode(),
                                        response.bodyToMono(String.class),
                                        response.headers().asHttpHeaders())
                        )))
                .bodyToMono(String.class) // 응답 받을 객체 String.class 설정.
                .block(); // Send & Receive
        log.info(">> Send MPI Response : [{}]", result);

        // 응답 데이터 Mapper 세팅.
        return MpiBasicInfoMapper.builder()
                .wtid(activityMpiBasicInfo.getMpiTrnsId().getWtid())
                .mid(activityMpiBasicInfo.getMid())
                .message(result)
                .url(mpiUrl)
                .build();
    }
}

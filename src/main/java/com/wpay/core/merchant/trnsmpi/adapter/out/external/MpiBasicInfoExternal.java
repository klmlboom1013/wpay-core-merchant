package com.wpay.core.merchant.trnsmpi.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.CustomWebClientTimeoutException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.infra.WebClientUseTemplate;
import com.wpay.core.merchant.trnsmpi.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.MpiBasicInfoExternalPort;
import com.wpay.core.merchant.trnsmpi.application.port.out.external.MpiBasicInfoExternalVersion;
import com.wpay.core.merchant.trnsmpi.domain.ActivityMpiBasicInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
class MpiBasicInfoExternal implements MpiBasicInfoExternalPort {

    @Qualifier(value = "mpiWebClient")
    private final WebClient mpiWebClient;

    private final WebClientUseTemplate webClientUseTemplate;

    @Value("${external.target.mpi.basic-info-url}")
    private String mpiBasicInfoUrl;

    @Override public MpiBasicInfoExternalVersion getVersionCode() { return MpiBasicInfoExternalVersion.v1; }

    @Override
    public MpiBasicInfoMapper sendMpiBasicInfoRun(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final String mid = activityMpiBasicInfo.getMid();

        URI uri;
        try {
            uri = new URI(String.format("%s?mid=%s&ch=WPAY", mpiBasicInfoUrl, mid));
        } catch (URISyntaxException e) {
            log.debug("[{}][{}] MPI URL \"{}\" URISyntaxException: {}", mid, wtid, mpiBasicInfoUrl, e.getMessage());
            throw new CustomException(CustomExceptionData.builder()
                    .errorCode(ErrorCode.HTTP_STATUS_500).e(e).build());
        }

        String result;
        try {
            result = this.webClientUseTemplate.httpGetSendRetrieveToAppForm(this.mpiWebClient, uri);
        } catch (CustomWebClientTimeoutException ex) {
            log.error("CustomWebClientTimeoutException : {}", ex.getEx().getClass().getSimpleName());
            throw new CustomException(CustomExceptionData.builder().errorCode(ex.getErrorCode()).e(ex).build());
        }
        log.info(">> Send MPI Response : [{}]", result);

        // 응답 데이터 Mapper 세팅.
        return MpiBasicInfoMapper.builder()
                .wtid(activityMpiBasicInfo.getMpiTrnsId().getWtid())
                .mid(activityMpiBasicInfo.getMid())
                .message(result)
                .url(uri.toString())
                .build();
    }
}

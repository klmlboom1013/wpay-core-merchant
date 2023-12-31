package com.wpay.core.merchant.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.exception.webclient.CustomWebClientRequestException;
import com.wpay.common.global.exception.webclient.CustomWebClientResponseException;
import com.wpay.common.global.exception.webclient.CustomWebClientTimeoutException;
import com.wpay.common.global.infra.WebClientUseTemplate;
import com.wpay.core.merchant.application.port.out.dto.MpiBasicInfoMapper;
import com.wpay.core.merchant.application.port.out.external.MpiBasicInfoExternalPort;
import com.wpay.core.merchant.application.port.out.external.MpiBasicInfoExternalVersion;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.global.cfgclient.ResourceEnv;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final ResourceEnv resourceEnv;

    @Override public MpiBasicInfoExternalVersion getVersionCode() { return MpiBasicInfoExternalVersion.v1; }

    @Override
    public MpiBasicInfoMapper sendMpiBasicInfoRun(@NonNull ActivityMpiBasicInfo activityMpiBasicInfo) {
        final String wtid = activityMpiBasicInfo.getMpiTrnsId().getWtid();
        final String jnoffcId = activityMpiBasicInfo.getJnoffcId();
        log.info("[{}][{}] 가맹점 기준정보 조회 MPI 통신 연동 시작.", jnoffcId, wtid);

        final String mpiFullUrl = String.format("%s?mid=%s&ch=WPAY", resourceEnv.getMpiUrl(), jnoffcId);
        log.info("[{}][{}] MPI 통신 URL: {}", jnoffcId, wtid, mpiFullUrl);

        URI uri;
        try {
            uri = new URI(mpiFullUrl);
        } catch (URISyntaxException e) {
            log.error("[{}][{}] MPI URL \"{}\" URISyntaxException: {}", jnoffcId, wtid, mpiFullUrl, e.getMessage());
            throw new CustomException(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_500).e(e).build());
        }

        String result;
        try {
            result = this.webClientUseTemplate.httpGetSendRetrieveToAppForm(this.mpiWebClient, uri);
        } catch (CustomWebClientRequestException ex) {
            ex.setMapper(MpiBasicInfoMapper.builder().wtid(wtid).jnoffcId(jnoffcId).url(uri.toString()).message(ex.getMessage()).build());
            throw ex;
        } catch (CustomWebClientResponseException ex) {
            ex.setMapper(MpiBasicInfoMapper.builder().wtid(wtid).jnoffcId(jnoffcId).url(uri.toString()).message(ex.getMessage()).build());
            throw ex;
        } catch (CustomWebClientTimeoutException ex) {
            ex.setMapper(MpiBasicInfoMapper.builder().wtid(wtid).jnoffcId(jnoffcId).url(uri.toString()).message(ex.getMessage()).build());
            throw ex;
        }
        log.info(">> Send MPI Response : [{}]", result);

        // 응답 데이터 Mapper 세팅.
        return MpiBasicInfoMapper.builder()
                .wtid(wtid)
                .jnoffcId(jnoffcId)
                .message(result)
                .url(uri.toString())
                .build();
    }
}

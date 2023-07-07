package com.wpay.core.merchant.adapter.in.restful;

import com.wpay.core.merchant.adapter.in.dto.MerchantInfo;
import com.wpay.core.merchant.application.port.in.PortInFactory;
import com.wpay.core.merchant.global.annotation.WebAdapter;
import com.wpay.core.merchant.global.dto.BaseRestFulResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@WebAdapter
@RestController
@RequiredArgsConstructor
class MerchantController {

    private final PortInFactory portInFactory;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{version}/merchant-info/{mid}/{option}")
    ResponseEntity<BaseRestFulResponse> merchantInfo (
            @PathVariable String version, @PathVariable String mid, @PathVariable String option) {

        log.info("====== Search Merchant Basic Info Start =====");

        return ResponseEntity.ok().body(
                portInFactory.getUseCase(MerchantInfo.jobCode).execute(
                        MerchantInfo.builder()
                                .version(version)
                                .mid(mid)
                                .option(option)
                                .build()));
    }
}

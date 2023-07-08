package com.wpay.core.merchant.adapter.in.web;

import com.wpay.core.merchant.adapter.in.dto.MerchantInfo;
import com.wpay.core.merchant.application.port.in.PortInFactory;
import com.wpay.core.merchant.global.annotation.WebAdapter;
import com.wpay.core.merchant.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@WebAdapter
@RestController
@RequiredArgsConstructor
class MerchantController {

    private final PortInFactory portInFactory;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{version}/merchant-info")
    ResponseEntity<BaseResponse> merchantInfo (@PathVariable String version, @RequestBody MerchantInfo merchantInfo) {
        log.info("====== Search Merchant Basic Info Start =====");
        return ResponseEntity.ok().body(portInFactory
                        .getUseCase(MerchantInfo.jobCode, version)
                        .execute(merchantInfo));
    }
}

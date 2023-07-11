package com.wpay.core.merchant.adapter.in.web;

import com.wpay.core.merchant.application.port.in.MpiBasicInfoCommand;
import com.wpay.core.merchant.application.port.in.MpiBasicInfoUseCaseFactory;
import com.wpay.core.merchant.global.annotation.WebAdapter;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.ApiVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@WebAdapter
@RestController
@RequiredArgsConstructor
class MpiBasicInfoController {

    private final MpiBasicInfoUseCaseFactory mpiBasicInfoUseCaseFactory;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{version}/mpiBasicInfo")
    ResponseEntity<BaseResponse> merchantInfo (@PathVariable String version,
                                               @RequestBody MpiBasicInfoCommand mpiBasicInfoCommand) {
        return ResponseEntity.ok().body(
                mpiBasicInfoUseCaseFactory.getUseCase(ApiVersion.getInstance(version))
                        .execute(mpiBasicInfoCommand));
    }
}

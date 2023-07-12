package com.wpay.core.merchant.adapter.in.web;

import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoCommand;
import com.wpay.core.merchant.global.factory.port.in.InPortFactory;
import com.wpay.core.merchant.global.annotation.WebAdapter;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.VersionCode;
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

    private final InPortFactory inPortFactory;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{version}/mpi-basic-info")
    ResponseEntity<BaseResponse> merchantInfo (@PathVariable String version,
                                               @RequestBody MpiBasicInfoCommand mpiBasicInfoCommand) {
        final BaseResponse result =
                (BaseResponse)this.inPortFactory.getUseCase(VersionCode.getInstance(version), MpiBasicInfoCommand.jobCode)
                .execute(mpiBasicInfoCommand);
        log.info("MpiBasicInfo Result - [{}]", result);
        return ResponseEntity.ok().body(result);
    }
}

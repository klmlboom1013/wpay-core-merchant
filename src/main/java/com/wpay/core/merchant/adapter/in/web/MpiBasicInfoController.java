package com.wpay.core.merchant.adapter.in.web;

import com.wpay.common.global.annotation.WebAdapter;
import com.wpay.common.global.port.PortInFactory;
import com.wpay.core.merchant.application.port.in.dto.MpiBasicInfoCommand;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCaseVersion;
import com.wpay.core.merchant.global.cfgclient.EcomerceEnv;
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

    private final PortInFactory portInFactory;

    private final EcomerceEnv ecomerceEnv;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/merchant/{version}/mpi-basic-info")
    ResponseEntity<?> merchantInfoRun (@PathVariable String version,
                                       @RequestBody MpiBasicInfoCommand mpiBasicInfoCommand) {

        log.info(">>>> properties tokenExpirationTime : {}", ecomerceEnv.getTokenExpirationTime());

        return ResponseEntity.ok().body(
                portInFactory.getUseCasePort(
                        MpiBasicInfoUseCaseVersion.getInstance(version).name(),
                        mpiBasicInfoCommand.getJobCodes().toString())
                        .execute(mpiBasicInfoCommand));

    }
}

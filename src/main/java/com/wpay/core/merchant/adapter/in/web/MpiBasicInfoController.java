package com.wpay.core.merchant.adapter.in.web;

import com.wpay.common.global.annotation.WebAdapter;
import com.wpay.common.global.port.PortInFactory;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoCommand;
import com.wpay.core.merchant.global.enums.MpiBasicInfoVersion;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/merchant/{version}/mpi-basic-info")
    ResponseEntity<?> merchantInfoRun (@PathVariable String version,
                                       @RequestBody MpiBasicInfoCommand mpiBasicInfoCommand) {
        return ResponseEntity.ok().body(
                portInFactory.getUseCasePort(
                        MpiBasicInfoVersion.getInstance(version).name(),
                        mpiBasicInfoCommand.getJobCodes().toString())
                        .execute(mpiBasicInfoCommand));

    }
}

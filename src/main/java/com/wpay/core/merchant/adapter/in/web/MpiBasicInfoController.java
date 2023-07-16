package com.wpay.core.merchant.adapter.in.web;

import com.wpay.common.global.annotation.WebAdapter;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoCommand;
import com.wpay.core.merchant.application.port.in.usecase.MpiBasicInfoUseCaseFactory;
import com.wpay.core.merchant.enums.MpiBasicInfoVersion;
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
    @GetMapping(path = "/{version}/mpi-basic-info")
    ResponseEntity<BaseResponse> merchantInfo (@PathVariable String version,
                                               @RequestBody MpiBasicInfoCommand mpiBasicInfoCommand) {

        return ResponseEntity.ok().body(mpiBasicInfoUseCaseFactory
                .getMpiBasicInfoUseCase(MpiBasicInfoVersion.getInstance(version), MpiBasicInfoCommand.MPI_BASIC_INFO_JOB_CODE)
                .execute(mpiBasicInfoCommand));
    }
}

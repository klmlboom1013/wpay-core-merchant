package com.wpay.core.merchant.application.service;

import com.wpay.core.merchant.adapter.in.dto.MerchantInfo;
import com.wpay.core.merchant.application.port.in.SendMpiBasicInfoUseCase;
import com.wpay.core.merchant.application.port.out.LoadMpiBasicInfoPort;
import com.wpay.core.merchant.application.port.out.SaveMpiBasicInfoPort;
import com.wpay.core.merchant.domain.ActivityMpiBasicInfo;
import com.wpay.core.merchant.domain.MpiBasicInfoMapper;
import com.wpay.core.merchant.global.annotation.UseCase;
import com.wpay.core.merchant.global.dto.BaseResponse;
import com.wpay.core.merchant.global.enums.ApiVersion;
import com.wpay.core.merchant.global.enums.JobCode;
import com.wpay.core.merchant.global.enums.SearchMpiBasicInfoOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class SendMpiBasicInfoService implements SendMpiBasicInfoUseCase {

    private final LoadMpiBasicInfoPort loadMpiBasicInfoPort;
    private final SaveMpiBasicInfoPort saveMpiBasicInfoPort;


    @Override public ApiVersion getVersion() { return ApiVersion.v1; }
    @Override public final JobCode getApiType() { return JobCode.SendMpiBasicInfo; }

    @Override
    public final BaseResponse execute(Object dto) {
        if(Boolean.FALSE.equals(dto instanceof MerchantInfo))
            throw new IllegalArgumentException("인입 객체 타입 검증 오류 입니다. [Argument DTO is not MerchantInfo.]");

        final MerchantInfo merchantInfo = (MerchantInfo) dto;

        return this.sendMpiBasicInfo(ActivityMpiBasicInfo.builder()
                .mpiTrnsId(ActivityMpiBasicInfo.MpiTrnsId.builder().wtid(merchantInfo.getWtid()).build())
                .option(SearchMpiBasicInfoOption.getInstance(merchantInfo.getOption().toString()))
                .jobCode(MerchantInfo.jobCode)
                .mid(merchantInfo.getMid())
                .build());
    }

    /**
     * MPI 기준 정보 조회 시작.
     */
    @Override
    public BaseResponse sendMpiBasicInfo(final ActivityMpiBasicInfo activityMpiBasicInfo) {

        /* MPI 기준 정보 조회 요청 전 WITD 로 이미 조회 이력이 있는지 확인 함. */
        final MpiBasicInfoMapper mpiBasicInfoMapper = this.loadMpiBasicInfoPort.readActivities(activityMpiBasicInfo);

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(mpiBasicInfoMapper)
                .build();
    }
}

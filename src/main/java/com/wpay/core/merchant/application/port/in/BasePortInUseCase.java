package com.wpay.core.merchant.application.port.in;

import com.wpay.core.merchant.global.dto.BaseRestFulResponse;
import com.wpay.core.merchant.global.enums.JobCode;

public interface BasePortInUseCase {

    /** controller 에서 어떤 service 를 사용할 거지 구분 하기 위한 값 */
    JobCode getApiType();

    /** controller 와 service 를 연결 해주는 기능. s*/
    BaseRestFulResponse execute (Object dto);
}

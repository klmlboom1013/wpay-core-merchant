package com.wpay.core.merchant.enums;

import lombok.Getter;

/**
 * WPAY 업무 구분 코드
 */
public enum MpiBasicInfoJobCode {

    /** MPI 기준 정보 조회 */
    JOB_CODE_20("20")
    ;

    @Getter
    private final String code;

    MpiBasicInfoJobCode(final String code){
        this.code=code;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    public boolean equals(MpiBasicInfoJobCode code) {
        return this.name().equals(code.name());
    }

    public static MpiBasicInfoJobCode getInstance(String code) {
        for(MpiBasicInfoJobCode o : MpiBasicInfoJobCode.values())
            if(o.equals(code)) return o;
        return null;
    }
}

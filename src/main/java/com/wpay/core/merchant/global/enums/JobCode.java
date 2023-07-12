package com.wpay.core.merchant.global.enums;

import lombok.Getter;

/**
 * WPAY 업무 구분 코드
 */
public enum JobCode {

    /** MPI 기준 정보 조회 */
    JOB_CODE_20("20")
    ;

    @Getter
    private final String code;

    JobCode(final String code){
        this.code=code;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    public static JobCode getInstance(String code) {
        for(JobCode o : JobCode.values())
            if(o.equals(code)) return o;
        return null;
    }
}

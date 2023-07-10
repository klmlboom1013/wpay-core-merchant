package com.wpay.core.merchant.global.enums;

/**
 * 가맹점 기준 정보 조회 옵션
 */
public enum SearchMpiBasicInfoOption {
    /** 최대 할부 개월 수 */
    MQ,
    /** 카드 포인트 사용 여부 */
    CP,
    /** 쿠폰 즉시 할인 이벤트 리스트 */
    CE,
    /** 무이자 할부 정보 */
    NI,
    /** 사용 가능한 결제 수단 정보 */
    PK,
    /** 사용 가능한 카드사 */
    UC,
    /** BC카드 발급사 정보 */
    BI,
    /** 모든 옵션 ALL */
    AL;
    public static SearchMpiBasicInfoOption getInstance(String code) {
        for(SearchMpiBasicInfoOption o : SearchMpiBasicInfoOption.values())
            if(o.toString().equals(code)) return o;
        throw new NullPointerException("지원 하지 않은 가맹점 기준 정보 조회 option 입니다.");
    }
}

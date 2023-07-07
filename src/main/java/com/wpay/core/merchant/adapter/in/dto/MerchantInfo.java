package com.wpay.core.merchant.adapter.in.dto;

import com.wpay.core.merchant.global.dto.SelfValidating;
import com.wpay.core.merchant.global.enums.JobCode;
import lombok.*;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Value
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MerchantInfo extends SelfValidating<MerchantInfo> {

    public static final JobCode jobCode = JobCode.SendMpiBasicInfo;

    @NotNull
    MerchantInfoSearchOptions option;
    @NotNull
    @Size(min=10, max=20)
    String mid;

    @Builder
    public MerchantInfo(@NonNull String mid, @NonNull String option) {

        this.option = MerchantInfoSearchOptions.getInstance(option.toUpperCase());

        if(Objects.isNull(this.option))
            throw new ValidationException("지원 하지 않은 가맹점 기준 정보 조회 option 입니다.");

        this.mid = mid;

        this.validateSelf();
    }

    /** 가맹점 기준 정보 조회 옵션 */
    public enum MerchantInfoSearchOptions {
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
        public static MerchantInfoSearchOptions getInstance(String code) {
            for(MerchantInfoSearchOptions o : MerchantInfoSearchOptions.values())
                if(o.toString().equals(code)) return o;
            return null;
        }
    }
}

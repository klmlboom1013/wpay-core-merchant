package com.wpay.core.merchant.adapter.in.dto;

import com.wpay.core.merchant.global.dto.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Value
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MerchantInfo extends SelfValidating<MerchantInfo> {
    @NotNull
    MerchantInfoVersion version;
    @NotNull
    MerchantInfoSearchOptions option;
    @NotNull
    @Size(min=10, max=20)
    String mid;

    @Builder
    public MerchantInfo(@NonNull String version, @NonNull String mid, @NonNull String option) {
        this.version = MerchantInfoVersion.getInstance(version.toUpperCase());
        this.option = MerchantInfoSearchOptions.getInstance(option.toUpperCase());
        if(Objects.isNull(this.version)) throw new ValidationException("This is the wrong version.");
        if(Objects.isNull(this.option)) throw new ValidationException("This is the wrong option.");

        this.mid = mid;

        this.validateSelf();
    }

    /** 기준 정보 조회 API 버전 정보 */
    public enum MerchantInfoVersion {
        V1;
        public static MerchantInfoVersion getInstance(String version) {
            for(MerchantInfoVersion o : MerchantInfoVersion.values())
                if(o.toString().equals(version)) return o;
            return null;
        }
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

package com.wpay.core.merchant.trnsmpi.application.port.out.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Getter
@ToString
@EqualsAndHashCode
public class MpiBasicInfoMapper {
    private final String wtid;
    private final String mid;
    private final String message;
    private final String url;
    private SendMpiBasicInfoResult sendMpiBasicInfoResult;

    @Builder
    public MpiBasicInfoMapper(String wtid, String mid, String message, String url) {
        this.wtid = wtid;
        this.mid = mid;
        this.url = url;
        this.message = this.convertMessage(message);
    }

    /**
     * MPI 통신 응답 중 불필요한 필드는 삭제 하여 message 를 재작성 한다.
     */
    private String convertMessage(@NotBlank(message = "MPI 기준 정보 조회 응답 정보가 없습니다.") String message) {
        final List<String> list = new ArrayList<>();
        Arrays.stream(message.split("\\|")).iterator().forEachRemaining(e -> {
            e = e.trim();
            if(Strings.isNotBlank(e.trim())){
                if((e.lastIndexOf(":") == e.length()-1) || (e.lastIndexOf("^") == e.length()-1)|| (e.lastIndexOf("&") == e.length()-1))
                    e = e.substring(0, e.length()-1);
                if(e.contains("card_max_quota")){
                    log.debug(">>> card_max_quota: {}", e.trim());
                }
                list.add(e.trim());
            }
        });
        log.debug("list length : {}", list.size());

        final AtomicReference<String> resultCode = new AtomicReference<>("");
        final AtomicReference<String> mpiStatus = new AtomicReference<>("");
        final StringBuilder sb = new StringBuilder();

        list.stream().iterator().forEachRemaining((e) -> {
            if(e.indexOf("retcode=") ==0) resultCode.set(e.replace("retcode=", "").trim());
            else if (e.indexOf("MID_STATUS=") == 0) mpiStatus.set(e.replace("MID_STATUS=", "").trim());
            else if (e.indexOf("CD_WPAY=") == 0) sb.append(e).append("|");
            else if (e.indexOf("VISA_3D=") == 0) sb.append(e).append("|");
            else if (e.indexOf("NORMAL_CARD=") == 0) sb.append(e).append("|");
            else if (e.indexOf("nointwithprice=") == 0) sb.append(e).append("|");
            else if (e.indexOf("cardpoint_list=") == 0) sb.append(e).append("|");
            else if (e.indexOf("OPENBANK_CPID=") == 0) sb.append(e).append("|");
            else if (e.indexOf("OPENBANK_MID=") == 0) sb.append(e).append("|");
            else if (e.indexOf("COUPON_WPAY=") == 0) sb.append(e).append("|");
            else if (e.indexOf("bank_used_list=") == 0) sb.append(e).append("|");
            else if (e.indexOf("card_max_quota=") == 0) sb.append(e).append("|");
            else if (e.indexOf("FLG_CASHRECEIPT=") == 0) sb.append(e).append("|");
            else if (e.indexOf("acceptmethod=") == 0) sb.append(e).append("|");
        });

        // MPI 통신 결과 및 MID 상태 값 저장.
        this.sendMpiBasicInfoResult = SendMpiBasicInfoResult.builder()
                .resultCode(resultCode.get())
                .midStatus(mpiStatus.get())
                .build();

        String result = sb.toString();

        // result 맨 뒤 "|" 제거
        if(result.lastIndexOf("|") == result.length()-1)
            result = result.substring(0, result.length()-1);

        log.info("MPI 응답 메시지 미사용 필드 삭제 결과: \n{} ", result);

        return result;
    }

    @Value
    @Getter
    @ToString
    public static class SendMpiBasicInfoResult {
        String resultCode;
        String midStatus;
        MpiReceiveResult mpiReceiveResult;
        MpiReceiveMpiStatus mpiReceiveMpiStatus;

        @Builder
        public SendMpiBasicInfoResult(String resultCode, String midStatus){
            this.resultCode=resultCode;
            this.midStatus=midStatus;
            this.mpiReceiveResult = MpiReceiveResult.getInstance(resultCode);
            this.mpiReceiveMpiStatus = MpiReceiveMpiStatus.getInstance(midStatus);
        }
    }

    /**
     * MPI 응답 코드
     */
    @Getter
    @AllArgsConstructor
    public enum MpiReceiveResult {
        RETCODE_SUCCESS("000"),
        RETCODE_FAIL(""),;
        private final String code;
        public boolean equals(String code) { return this.code.equals(code); }
        public static MpiReceiveResult getInstance(String code) {
            for(MpiReceiveResult o : MpiReceiveResult.values())
                if(o.getCode().equals(code)) return o;
            return RETCODE_FAIL;
        }
    }

    /**
     * MPI 응답 MPI 상태 코드
     */
    @Getter
    @AllArgsConstructor
    public enum MpiReceiveMpiStatus {
        STATUS_OK("0"),
        STATUS_FAIL("");
        private final String code;
        public boolean equals(String code) { return this.code.equals(code); }
        public static MpiReceiveMpiStatus getInstance(String code) {
            for(MpiReceiveMpiStatus o : MpiReceiveMpiStatus.values())
                if(o.getCode().equals(code)) return o;
            return STATUS_FAIL;
        }
    }
}

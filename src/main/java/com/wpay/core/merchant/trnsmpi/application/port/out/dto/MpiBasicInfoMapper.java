package com.wpay.core.merchant.trnsmpi.application.port.out.dto;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Getter
@ToString
@EqualsAndHashCode
public class MpiBasicInfoMapper {

    public static final String MPI_RSLT_CD_SUCCESS = "000";
    public static final String MPI_STATUS_CD_ACTIVE = "0";

    private final String wtid;
    private final String mid;
    private final String message;

    @Setter private SendMpiBasicInfoResult sendMpiBasicInfoResult;

    @Setter private String url;


    @Builder
    public MpiBasicInfoMapper(String wtid, String mid, String message, String url) {
        this.wtid = wtid;
        this.mid = mid;
        this.url = url;

        /* 기준정보 조회 결과 코드 Fetch */
        final String[] resultCode = new String[1];
        Arrays.stream(message.trim().split("\\|")).anyMatch(str -> {
            if (Strings.isBlank(str) || Boolean.FALSE.equals(str.contains("="))) { return false; } // continue
            final String[] data = str.split("=");
            if(Boolean.FALSE.equals("retcode".equals(data[0]))) { return false; } // continue
            resultCode[0] = data[1];
            return true; // break;
        });
        log.info("MPI 기준정보 조회 결과 코드 [resultCode:{}]", resultCode[0]);

        /* MPI 응답 성공 코드 "000" */
        if (MPI_RSLT_CD_SUCCESS.equals(resultCode[0])) {
            this.message = this.convertMessage(message);
            return; // 불 필요한 MPI 응답 메시지 제거 후 Mapper 생성자 프로세스 종료.
        }

        /* MPI 기준정보 조회 응답 실패 코드면 에러 메시지 Fetch 진행 */
        final String[] resultMsg = new String[1];
        Arrays.stream(message.trim().split("\\|")).anyMatch(str -> {
            if (Strings.isBlank(str) || str.contains("=")) { return false; } // continue
            resultMsg[0] = str.trim();
            return true; // break;
        });

        this.sendMpiBasicInfoResult = SendMpiBasicInfoResult.builder()
                .resultCode(resultCode[0])
                .errorMsg(String.format("[%s] %s", resultCode[0], resultMsg[0]))
                .midStatus("")
                .build();

        this.message = message.trim();

        log.info("MPI 기준정보 조회 결과 메시지 [resultCode:{}]", resultMsg[0]);
    }

    /**
     * MPI 통신 응답 중 불 필요한 필드는 삭제 하여 message 를 재작성 한다.
     */
    private String convertMessage(@NonNull String message) {
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
        String errorMsg;

        @Builder
        public SendMpiBasicInfoResult(String resultCode, String midStatus, String errorMsg) {
            this.resultCode=resultCode;
            this.midStatus=midStatus;
            String tempErrorMsg = MPI_RSLT_CD_SUCCESS.equals(resultCode) ? "" : errorMsg;
            if (Boolean.FALSE.equals(MPI_RSLT_CD_SUCCESS.equals(this.resultCode)) && Strings.isBlank(tempErrorMsg))
                tempErrorMsg = String.format("[%s] MID 기준정보 조회가 실패 했습니다. 관리자에게 문의 바랍니다.", this.resultCode);
            else if(MPI_RSLT_CD_SUCCESS.equals(this.resultCode) && Boolean.FALSE.equals(MPI_STATUS_CD_ACTIVE.equals(this.midStatus)))
                tempErrorMsg = String.format("[%s][%s] MID 기준정보 조회는 정상이나, 사용 가능한 MID 상태 값이 아닙니다. 관리자에게 문의 바랍니다.", this.resultCode, this.midStatus);
            this.errorMsg = tempErrorMsg;
        }
    }
}

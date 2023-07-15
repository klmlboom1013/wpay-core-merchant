package com.wpay.core.merchant.domain;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.annotate.JsonIgnore;
import org.apache.logging.log4j.util.Strings;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Getter
@EqualsAndHashCode(callSuper = false)
public class MpiBasicInfo {
    private final String wtid;
    private final String mid;
    private final Map<String, Object> data = new HashMap<>();

    @JsonIgnore
    private final SendMpiBasicInfoResult sendMpiBasicInfoResult = new SendMpiBasicInfoResult();

    @Builder
    public MpiBasicInfo(String wtid, String mid, String message) {
        this.wtid = wtid;
        this.mid = mid;
        this.setMapperData(message);
    }

    /**
     * MPI 기준 정보 조회 결과 / MID 상태(0 정상 -> 저장 시 0을 00 으로 변환 하여 저장.)
     */
    @Getter
    @Setter
    @ToString
    public static class SendMpiBasicInfoResult {
        String resultCode;
        String midStatusCd;
    }


    private void setMapperData (@NotBlank(message = "MPI 기준 정보 조회 응답 정보가 없습니다.") String message) {
        final List<String> list = new ArrayList<>();
        Arrays.stream(message.split("\\|")).iterator().forEachRemaining(e -> {
            e = e.trim();
            if(Strings.isNotBlank(e.trim())){
                if((e.lastIndexOf(":") == e.length()-1) || (e.lastIndexOf("^") == e.length()-1)|| (e.lastIndexOf("&") == e.length()-1))
                    e= e.substring(0, e.length()-1);
                list.add(e.trim());
            }
        });
        log.debug("list length : {}", list.size());

        list.stream().iterator().forEachRemaining((e) -> {
            String val;
            if(e.indexOf("retcode=") ==0) {
                val = e.replace("retcode=", "").trim();
                sendMpiBasicInfoResult.setResultCode(("000".equals(val)) ? "0000" : val);
            }
            else if (e.indexOf("MID_STATUS=") == 0) {
                val = e.replace("MID_STATUS=", "").trim();
                sendMpiBasicInfoResult.setMidStatusCd(("0".equals(val)) ? "00" : val);
            }
            else if (e.indexOf("CD_WPAY=") == 0) {
                val = e.replace("CD_WPAY=", "");
                this.data.put("payMethod", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (e.indexOf("VISA_3D=") == 0) {
                val = e.replace("VISA_3D=", "");
                this.data.put("cardCd1", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (e.indexOf("NORMAL_CARD=") == 0) {
                val = e.replace("NORMAL_CARD=", "");
                this.data.put("cardCd2", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (e.indexOf("nointwithprice=") == 0) {
                val = e.replace("nointwithprice=", "");
                this.data.put("nointCard", new ArrayList<>(Arrays.asList(val.split("\\^"))));
            }
            else if (e.indexOf("cardpoint_list=") == 0) {
                this.data.put("cardpoint", new ArrayList<>());
            }
            else if (e.indexOf("OPENBANK_CPID=") == 0) {
                val = e.replace("OPENBANK_CPID=", "");
                this.data.put("openBankCpid", val);
            }
            else if (e.indexOf("OPENBANK_MID=") == 0) {
                val = e.replace("OPENBANK_MID=", "");
                this.data.put("openBankMid", val);
            }
            else if (e.indexOf("COUPON_WPAY=") == 0) {
                this.data.put("coupon", new ArrayList<>());
            }
            else if (e.indexOf("bank_used_list=") == 0) {
                val = e.replace("bank_used_list=", "");
                this.data.put("bankCd", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (e.indexOf("card_max_quota=") == 0) {
                val = e.replace("card_max_quota=", "");
                data.put("cardMaxQuota", new ArrayList<>(Arrays.asList(val.split("&"))));
            }
            else if (e.indexOf("acceptmethod=") == 0) {
                val = e.replace("acceptmethod=", "");
                this.data.put("useReceipt1", (val.indexOf("CASHRECEIPT") > 0) ? "Y" : "N");
            }
            else if (e.indexOf("FLG_CASHRECEIPT=") == 0) {
                this.data.put("useReceipt2", data.replace("FLG_CASHRECEIPT=", ""));
            }
        });
        log.info("Setting MPI Response to Mapper: \n{} ", this.data.toString());
    }
}

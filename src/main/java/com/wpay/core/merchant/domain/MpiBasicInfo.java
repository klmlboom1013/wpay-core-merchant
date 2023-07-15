package com.wpay.core.merchant.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;

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
    private final String message;

    private final Map<String, Object> mapper = new HashMap<>();

    private final SendMpiBasicInfoResult sendMpiBasicInfoResult = new SendMpiBasicInfoResult();

    @Builder
    public MpiBasicInfo(String wtid, String mid, String message) {
        this.wtid = wtid;
        this.mid = mid;
        this.message = message;
        this.setMapperData();
    }

    /**
     * MPI 기준 정보 조회 결과 / MID 상태(0 정상 -> 저장 시 0을 00 으로 변환 하여 저장.)
     */
    @Getter
    @Setter
    private static class SendMpiBasicInfoResult {
        private String resultCode;
        private String midStatusCd;
    }


    private void setMapperData () {
        if (Strings.isBlank(this.message))
           throw new NullPointerException("MPI 기준 정보 조회 응답 정보가 message 필드에 저장 되어 있지 않습니다.");

        final List<String> list = new ArrayList<>();
        Arrays.stream(this.message.split("\\|")).iterator().forEachRemaining(e -> {
            e=e.trim();
            if(Strings.isNotBlank(e.trim())){
                if((e.lastIndexOf(":") == e.length()-1) || (e.lastIndexOf("^") == e.length()-1)|| (e.lastIndexOf("&") == e.length()-1))
                    e= e.substring(0, e.length()-1);
                list.add(e.trim());
            }
        });
        log.debug("list length : {}", list.size());

        list.stream().iterator().forEachRemaining((data) -> {
            String val;
            if(data.indexOf("retcode=") ==0) {
                val = data.replace("retcode=", "").trim();
                sendMpiBasicInfoResult.setResultCode(("000".equals(val)) ? "0000" : val);
            }
            else if (data.indexOf("MID_STATUS=") == 0) {
                val = data.replace("MID_STATUS=", "").trim();
                sendMpiBasicInfoResult.setMidStatusCd(("0".equals(val)) ? "00" : val);
            }
            else if (data.indexOf("CD_WPAY=") == 0) {
                val = data.replace("CD_WPAY=", "");
                this.mapper.put("payMethod", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (data.indexOf("VISA_3D=") == 0) {
                val = data.replace("VISA_3D=", "");
                this.mapper.put("cardCd1", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (data.indexOf("NORMAL_CARD=") == 0) {
                val = data.replace("NORMAL_CARD=", "");
                this.mapper.put("cardCd2", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (data.indexOf("nointwithprice=") == 0) {
                val = data.replace("nointwithprice=", "");
                this.mapper.put("nointCard", new ArrayList<>(Arrays.asList(val.split("\\^"))));
            }
            else if (data.indexOf("cardpoint_list=") == 0) {
                this.mapper.put("cardpoint", new ArrayList<>());
            }
            else if (data.indexOf("OPENBANK_CPID=") == 0) {
                val = data.replace("OPENBANK_CPID=", "");
                this.mapper.put("openBankCpid", val);
            }
            else if (data.indexOf("OPENBANK_MID=") == 0) {
                val = data.replace("OPENBANK_MID=", "");
                this.mapper.put("openBankMid", val);
            }
            else if (data.indexOf("COUPON_WPAY=") == 0) {
                this.mapper.put("coupon", new ArrayList<>());
            }
            else if (data.indexOf("bank_used_list=") == 0) {
                val = data.replace("bank_used_list=", "");
                this.mapper.put("bankCd", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (data.indexOf("card_max_quota=") == 0) {
                val = data.replace("card_max_quota=", "");
                mapper.put("cardMaxQuota", new ArrayList<>(Arrays.asList(val.split("&"))));
            }
            else if (data.indexOf("acceptmethod=") == 0) {
                val = data.replace("acceptmethod=", "");
                this.mapper.put("useReceipt1", (val.indexOf("CASHRECEIPT") > 0) ? "Y" : "N");
            }
            else if (data.indexOf("FLG_CASHRECEIPT=") == 0) {
                this.mapper.put("useReceipt2", data.replace("FLG_CASHRECEIPT=", ""));
            }
        });
        log.info("Setting MPI Response to Mapper: \n{} ", this.mapper.toString());
    }
}

package com.wpay.core.merchant.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Log4j2
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CompleteMpiBasicInfo {

    @Setter
    private String wtid;

    @Setter
    @JsonProperty("mid")
    private String jnoffcId;

    private final Map<String, Object> mpiBasicInfos = new HashMap<>();

    @Builder
    public CompleteMpiBasicInfo(String wtid, String jnoffcId, String message) {
        this.wtid = wtid;
        this.jnoffcId = jnoffcId;
        this.setMapperData(message);
    }

    private void setMapperData (@NotBlank(message = "MPI 기준 정보 조회 응답 정보가 없습니다.") String message) {
        final List<String> list = new ArrayList<>(List.of(message.split("\\|")));
        final List<String> tempCardCodeList = new ArrayList<>();
        final Map<String, String> cashReceipt = new HashMap<>();
        list.stream().iterator().forEachRemaining((e) -> {
            String val;
            if (e.indexOf("nointwithprice=") == 0) {
                val = e.replace("nointwithprice=", "");
                List<Map<?,?>> nointCards = new ArrayList<>();
                final List<String> tmpList = new ArrayList<>(Arrays.asList(val.split("\\^")));
                tmpList.iterator().forEachRemaining(e1 -> {
                    final Map<String, Object> finalData = new HashMap<>();
                    final String[] kv = e1.split("-");
                    finalData.put("bankCardCode", kv[0]);
                    finalData.put("amount", kv[1]);
                    final String[] months = kv[3].split(":");
                    finalData.put("months", new ArrayList<>(Arrays.asList(months)));
                    nointCards.add(finalData);
                });
                this.mpiBasicInfos.put("nointCards", nointCards);
            }

            else if (e.indexOf("card_max_quota=") == 0) {
                val = e.replace("card_max_quota=", "");
                List<Map<?,?>> maxQuotaList = new ArrayList<>();
                final List<String> tmpList = new ArrayList<>(Arrays.asList(val.split("&")));
                tmpList.iterator().forEachRemaining(e1 -> {
                    final Map<String, String> finalData = new HashMap<>();
                    final String[] kv = e1.split("=");
                    final String[] values = kv[1].split(":");
                    finalData.put("bankCardCode", kv[0]);
                    finalData.put("geIntMaxQuota", values[0]);
                    if(values.length > 1)
                        finalData.put("noIntMaxQuota", values[1]);
                    maxQuotaList.add(finalData);
                });
                mpiBasicInfos.put("cardMaxQuotas", maxQuotaList);
            }

            else if (e.indexOf("VISA_3D=") == 0) {
                val = e.replace("VISA_3D=", "");
                tempCardCodeList.addAll(Arrays.asList(val.split(":")));
            }
            else if (e.indexOf("NORMAL_CARD=") == 0) {
                val = e.replace("NORMAL_CARD=", "");
                tempCardCodeList.addAll(Arrays.asList(val.split(":")));
            }
            else if (e.indexOf("CD_WPAY=") == 0) {
                val = e.replace("CD_WPAY=", "");
                this.mpiBasicInfos.put("payMethods", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (e.indexOf("bank_used_list=") == 0) {
                val = e.replace("bank_used_list=", "");
                this.mpiBasicInfos.put("bankCodes", new ArrayList<>(Arrays.asList(val.split(":"))));
            }
            else if (e.indexOf("acceptmethod=") == 0) {
                val = e.replace("acceptmethod=", "");
                cashReceipt.put("acceptmethod", (val.indexOf("CASHRECEIPT") > 0) ? "Y" : "N");
            }

            else if (e.indexOf("cardpoint_list=") == 0) this.mpiBasicInfos.put("cardPointList", new ArrayList<>());
            else if (e.indexOf("OPENBANK_CPID=") == 0) this.mpiBasicInfos.put("openBankCpid", e.replace("OPENBANK_CPID=", ""));
            else if (e.indexOf("OPENBANK_MID=") == 0) this.mpiBasicInfos.put("openBankMid", e.replace("OPENBANK_MID=", ""));
            else if (e.indexOf("COUPON_WPAY=") == 0) this.mpiBasicInfos.put("coupons", new ArrayList<>());
            else if (e.indexOf("FLG_CASHRECEIPT=") == 0) cashReceipt.put("flagCashreceipt", e.replace("FLG_CASHRECEIPT=", ""));
        });
        this.mpiBasicInfos.put("cardCodes", tempCardCodeList);
        this.mpiBasicInfos.put("cashreceiptUseYn", ("Y".equals(cashReceipt.get("acceptmethod"))
                && "1".equals(cashReceipt.get("flagCashreceipt"))) ? "Y" : "N");
        log.info("Setting MPI Response to Mapper: \n{} ", this.mpiBasicInfos.toString());
    }
}

package com.wpay.core.merchant.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpay.common.global.config.SecurityConfig;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthSmsCommand;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.CellPhoneAuthVerifyCommand;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Field;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class CellPhoneAuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void smsAuthNumbRun() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                        .post("/mobilians/v1/sms-auth-numb")
                        .header("content-type", "application/json")
                        .with(csrf())
                        .content(getSmsAuthNumbCommandJsonStr())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("\n++++++++++++++++++++");
        System.out.println("[ Response ]");
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
        System.out.println("++++++++++++++++++++\n");
    }

    @Test
    void verifyAuthNumbRun() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post("/mobilians/v1/verify-auth-numb")
                .with(csrf())
                .content(getVerifyAuthNumbCommandJsonStr())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("\n++++++++++++++++++++");
        System.out.println("[ Response ]");
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
        System.out.println("++++++++++++++++++++\n");
    }


    private String getSmsAuthNumbCommandJsonStr() throws Exception {
        CellPhoneAuthSmsCommand cellPhoneAuthSmsCommand = new CellPhoneAuthSmsCommand();
        String mid = "INIwpayT03";
        Field field1 = BaseCommand.class.getDeclaredField("mid");
        field1.setAccessible(true);
        field1.set(cellPhoneAuthSmsCommand, mid);

        String wtid = "STWPY2023071054206201";
        Field field2 = BaseCommand.class.getDeclaredField("wtid");
        field2.setAccessible(true);
        field2.set(cellPhoneAuthSmsCommand, wtid);

        String hnum = "d3BheWNvcmVtb2R1bGUwMDSUmw5ALuLC79o4jNEpK8RiLgKMtsrzBtXNyLsMovRT6Rfmr7jJ/wWs9Jtyty7WEMu4dWRB6HsJ1MvpoSuSjUMfr5ZKbTBlLJrcIK2y927oybM1rS51+D150XLoIw8SP5BQjalBOtmaej+aRpm0uPX6n6QONax0RKn2ZkdeGEYlgd8JFX+QSLOrwtsHS9cTcA==";
        Field field3 = CellPhoneAuthSmsCommand.class.getDeclaredField("hnum");
        field3.setAccessible(true);
        field3.set(cellPhoneAuthSmsCommand, hnum);

        String socialNo2 = "d3BheWNvcmVtb2R1bGUwMPA/8xwLoi9Nq/tCjIdxv6Fp+g5Sdl25TCt/vciIQXT8f/FwIKljgkXopK6LKs5P4gjVpM6e7svgTi9AysYQ2RGveUkKR5HgHlh/cA3ATUW4wXXo1ubq2LQi8m15bq17IgT6YFUOqnpXTW3f5XRe0bhO5SCtrDsejwq0w+cEHjUVgGz4IChNfJFbPVAA+gAA2A==";
        Field field4 = CellPhoneAuthSmsCommand.class.getDeclaredField("socialNo2");
        field4.setAccessible(true);
        field4.set(cellPhoneAuthSmsCommand, socialNo2);

        String userNm = "d3BheWNvcmVtb2R1bGUwMGrvFq94OmkE1Zfny26ObAhUOQBXSh5w0xABDGvH/ZRkDw5S1CKG/X6uUo57E8jpCKsrPFhT8hFOS8dencmDzDzLkqesJND4fkeV20dRBZlQPjaG+Xk8SRa/BkJeXD0Y34sXWne1bwtD3SBS4BqTew28qPAX2IsD9QYuEjHMoNYabdm85jRHBhm3HXZorB7aZA==";
        Field field5 = CellPhoneAuthSmsCommand.class.getDeclaredField("userNm");
        field5.setAccessible(true);
        field5.set(cellPhoneAuthSmsCommand, userNm);

        String birthday = "d3BheWNvcmVtb2R1bGUwMMKnD7Nedr8eHf3fpe/gUwWcjkwsZ20wj4zKaQa1VFvVMwNwFukOspH2s7l6gq+LIC3anPyk4rk24tcRs+kzhOap3542cN2Refr8tdq/g3KQw6QMVj3CB6xil2ofTwtDGNmfJKzcGm/g0yrBHLSK5+gZCTPenoOHkQ8SRAZjVPdxcwH4DbwZhR1pcjeJQy4UWQ==";
        Field field6 = CellPhoneAuthSmsCommand.class.getDeclaredField("birthday");
        field6.setAccessible(true);
        field6.set(cellPhoneAuthSmsCommand, birthday);

        String hcorp = "LGT";
        Field field7 = CellPhoneAuthSmsCommand.class.getDeclaredField("hcorp");
        field7.setAccessible(true);
        field7.set(cellPhoneAuthSmsCommand, hcorp);
        String result = new ObjectMapper().writeValueAsString(cellPhoneAuthSmsCommand);
        System.out.println("\n--------------------");
        System.out.println("[ @@  Request  @@ ]");
        System.out.println(result);
        System.out.println("--------------------\n");

        return result;
    }

    private String getVerifyAuthNumbCommandJsonStr() throws Exception {
        CellPhoneAuthVerifyCommand cellPhoneAuthVerifyCommand = new CellPhoneAuthVerifyCommand();
        String encAutnNumb = "d3BheWNvcmVtb2R1bGUwMAXhFAQZcSjTNWv3pjyzSkIaA/TF77c1/TdxYGHQsYele2a/T1kycV3SQlvCU00ebogZKNlZ4s76uh2LgsNV+uo1gLdKs0C4EwnkOT6Lj58RxdxClZOgte+zhTmpJLt4GikV0dbF/8NdyGIPSsDfeh/m8HHdVSB+SLdJtymye2tOE0lNV2mdDVBwAHp0fiJmSg==";
        Field field = CellPhoneAuthVerifyCommand.class.getDeclaredField("authNumb");
        field.setAccessible(true);
        field.set(cellPhoneAuthVerifyCommand, encAutnNumb);

        String wtid = "STWPY2023071054206201";
        Field field1 = BaseCommand.class.getDeclaredField("wtid");
        field1.setAccessible(true);
        field1.set(cellPhoneAuthVerifyCommand, wtid);

        String mid = "INIwpayT03";
        Field field2 = BaseCommand.class.getDeclaredField("mid");
        field2.setAccessible(true);
        field2.set(cellPhoneAuthVerifyCommand, mid);

        String result = new ObjectMapper().writeValueAsString(cellPhoneAuthVerifyCommand);
        System.out.println("\n--------------------");
        System.out.println("[ @@  Request  @@ ]");
        System.out.println(result);
        System.out.println("--------------------\n");

        return result;
    }
}
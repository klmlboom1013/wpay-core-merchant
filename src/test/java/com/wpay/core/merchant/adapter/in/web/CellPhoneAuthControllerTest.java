package com.wpay.core.merchant.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.core.merchant.global.beans.SecurityBeans;
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
@Import(SecurityBeans.class)
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
        Field field1 = BaseCommand.class.getDeclaredField("jnoffcId");
        field1.setAccessible(true);
        field1.set(cellPhoneAuthSmsCommand, mid);

        String wtid = "STWPY2023071054206201";
        Field field2 = BaseCommand.class.getDeclaredField("wtid");
        field2.setAccessible(true);
        field2.set(cellPhoneAuthSmsCommand, wtid);

        String hnum = "tqstmy1YYbkPPYWmJJsRJw==";
        Field field3 = CellPhoneAuthSmsCommand.class.getDeclaredField("ecdCphno");
        field3.setAccessible(true);
        field3.set(cellPhoneAuthSmsCommand, hnum);

        String socialNo2 = "Px760eXCNuKjvNLvJJ1DkQ==";
        Field field4 = CellPhoneAuthSmsCommand.class.getDeclaredField("socialNo2");
        field4.setAccessible(true);
        field4.set(cellPhoneAuthSmsCommand, socialNo2);

        String userNm = "S3ne3QpcRP7U6Gctf0rY6A==";
        Field field5 = CellPhoneAuthSmsCommand.class.getDeclaredField("buyerNm");
        field5.setAccessible(true);
        field5.set(cellPhoneAuthSmsCommand, userNm);

        String birthday = "4HlAxW3pSgmcYqOCSxIDjw==";
        Field field6 = CellPhoneAuthSmsCommand.class.getDeclaredField("ecdBthDt");
        field6.setAccessible(true);
        field6.set(cellPhoneAuthSmsCommand, birthday);

        String hcorp = "LGT";
        Field field7 = CellPhoneAuthSmsCommand.class.getDeclaredField("mmtTccoDvdCd");
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
        String encAutnNumb = "Kn0aS5SATa9qI5oPiIPkYw==";
        Field field = CellPhoneAuthVerifyCommand.class.getDeclaredField("authNumb");
        field.setAccessible(true);
        field.set(cellPhoneAuthVerifyCommand, encAutnNumb);

        String wtid = "STWPY2023071054206201";
        Field field1 = BaseCommand.class.getDeclaredField("wtid");
        field1.setAccessible(true);
        field1.set(cellPhoneAuthVerifyCommand, wtid);

        String mid = "INIwpayT03";
        Field field2 = BaseCommand.class.getDeclaredField("jnoffcId");
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
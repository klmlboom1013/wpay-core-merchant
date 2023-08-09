package com.wpay.core.merchant.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpay.common.global.dto.BaseCommand;
import com.wpay.core.merchant.global.config.SecurityConfig;
import com.wpay.core.merchant.trnsmpi.application.port.in.dto.MpiBasicInfoCommand;
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
class MpiBasicInfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void merchantInfoRun() throws Exception {
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                        .get("/merchant/v1/mpi-basic-info")
                        .with(csrf())
                        .content(getMpiBasicInfoCommandJsonStr())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println("\n++++++++++++++++++++");
        System.out.println("[ Response ]");
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
        System.out.println("++++++++++++++++++++\n");
    }


    private String getMpiBasicInfoCommandJsonStr() throws Exception {
        MpiBasicInfoCommand mpiBasicInfoCommand = new MpiBasicInfoCommand();
        String mid = "INIwpayT03";
//        String mid = "INIwpayTTT";
        Field field1 = BaseCommand.class.getDeclaredField("mid");
        field1.setAccessible(true);
        field1.set(mpiBasicInfoCommand, mid);

        String wtid = "STWPY2023071054206201";
        Field field2 = BaseCommand.class.getDeclaredField("wtid");
        field2.setAccessible(true);
        field2.set(mpiBasicInfoCommand, wtid);

        String result = new ObjectMapper().writeValueAsString(mpiBasicInfoCommand);
        System.out.println("\n--------------------");
        System.out.println("[ @@  Request  @@ ]");
        System.out.println(result);
        System.out.println("--------------------\n");

        return result;
    }
}
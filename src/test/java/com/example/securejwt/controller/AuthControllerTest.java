package com.example.securejwt.controller;

import com.example.securejwt.security.SecurityConfig;
import com.example.securejwt.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
class AuthControllerTest {

    @Autowired
    MockMvc mvc;


    @Test
    void whenBadRequestThen400() throws Exception {
        this.mvc.perform(post("/authenticate"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenEnteredWrongDetailsThen401() throws Exception {
        this.mvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8)
                .content("{\"username\": \"badUser\",\"password\": \"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andReturn();

        this.mvc.perform(post("/authenticate")
                        .contentType(APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"ussrnam\": \"badUser\",\"asdasdasdasd\": \"wrongPassword\" ,\"asdasdasdasd\": \"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andReturn();

        this.mvc.perform(post("/authenticate")
                        .contentType(APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"ussrnam\": \"badUser\"}"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }



    @Test
    void authenticateUserAndCheckSecured200() throws Exception {

        MvcResult result = this.mvc.perform(post("/authenticate")
                        .contentType(APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"username\": \"user\",\"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();


        this.mvc.perform(get("/authenticate")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    public void mockUserStatusIsOK() throws Exception {
        this.mvc.perform(get("/authenticate")).andExpect(status().isOk());
    }



}
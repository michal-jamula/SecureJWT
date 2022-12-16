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


@WebMvcTest({HomeController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
class HomeControllerTest {

    @Autowired
    MockMvc mvc;


    @Test
    void whenUnauthenticatedThen401() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isUnauthorized());

        this.mvc.perform(get("/secure"))
                .andExpect(status().isUnauthorized());
    }



    @Test
    void authenticateUserAndCheckSecured200() throws Exception {

        MvcResult result = this.mvc.perform(post("/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content("{\"username\": \"Michal\",\"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(content().string("Hello, michal"));

        this.mvc.perform(get("/secure")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }


    @Test
    @WithMockUser
    public void mockUserStatusIsOK() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isOk());

        this.mvc.perform(get("/secure")).andExpect(status().isOk());
    }



}
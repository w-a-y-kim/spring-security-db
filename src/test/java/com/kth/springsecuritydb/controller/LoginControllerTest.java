package com.kth.springsecuritydb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kth.springsecuritydb.model.AdminMember;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_success_login() throws Exception {
        AdminMember adminMember = AdminMember.builder().userId("testUser").password("password").build();
        MockHttpServletRequestBuilder request = post("/login-process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminMember))
                .characterEncoding(StandardCharsets.UTF_8.name());

        ResultActions actions = mvc.perform(request).andDo(print());
        actions.andExpect(status().isOk());
    }

    @Test
    public void should_fail_login_invalidPassword() throws Exception {
        AdminMember adminMember = AdminMember.builder().userId("testUser").password("123123132").build();
        MockHttpServletRequestBuilder request = post("/login-process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminMember))
                .characterEncoding(StandardCharsets.UTF_8.name());

        ResultActions actions = mvc.perform(request).andDo(print());
        actions.andExpect(status().is(HttpServletResponse.SC_UNAUTHORIZED));
    }

    @Test
    public void should_success_myAccount() throws Exception {
        final String loginUserId = "testUser";

        MockHttpServletRequestBuilder request = get("/api/v1/my-account")
                .with(user(loginUserId).password("password").roles("ADMIN"))
                .characterEncoding(StandardCharsets.UTF_8.name());

        ResultActions actions = mvc.perform(request).andDo(print());
        AdminMember resultValue = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), AdminMember.class);
        assertEquals(loginUserId, resultValue.getUserId());
    }

    @Test
    public void should_fail_myAccount() throws Exception {
        final String loginUserId = "testUser";

        MockHttpServletRequestBuilder request = get("/api/v1/my-account")
                .with(user(loginUserId).password("password").roles("BASIC"))
                .characterEncoding(StandardCharsets.UTF_8.name());

        ResultActions actions = mvc.perform(request).andDo(print());
        actions.andExpect(status().is(HttpServletResponse.SC_FORBIDDEN));
    }
}
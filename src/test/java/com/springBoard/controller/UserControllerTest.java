package com.springBoard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void signupTest() throws Exception {
        String content = objectMapper.writeValueAsString(
                new UserSaveForm("abcd@gmail.com","testPass","testName")
        );
        mockMvc.perform(post("/user/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입_유효성검사() throws Exception{
        String content = objectMapper.writeValueAsString(
                new UserSaveForm("","","testName")
        );

        mockMvc.perform(post("/user/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("success").value(false))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 로그인() throws Exception{
        String content = objectMapper.writeValueAsString(
                new UserSaveForm("abcd@gmail.com","testPass","testName")
        );
        mockMvc.perform(post("/user/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        String loginContent = objectMapper.writeValueAsString(
                new UserLoginForm("abcd@gmail.com", "testPass")
        );
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(loginContent))
                .andDo(print())
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인_실패() throws Exception{
        String content = objectMapper.writeValueAsString(
                new UserSaveForm("abcd@gmail.com","testPass","testName")
        );
        mockMvc.perform(post("/user/signup")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        String loginContent = objectMapper.writeValueAsString(
                new UserLoginForm("abcd@gmail.com", "testPass2")
        );
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginContent))
                .andDo(print())
                .andExpect(jsonPath("success").value(false))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 로그인_유효성_실패() throws Exception{
        String content = objectMapper.writeValueAsString(
                new UserSaveForm("abcd@gmail.com","testPass","testName")
        );
        mockMvc.perform(post("/user/signup")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        String loginContent = objectMapper.writeValueAsString(
                new UserLoginForm("abcd", "testPass")
        );
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginContent))
                .andDo(print())
                .andExpect(jsonPath("success").value(false))
                .andExpect(status().is4xxClientError());
    }
}
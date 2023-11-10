package com.springBoard.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.token.model.Token;
import com.springBoard.user.model.TokenResponse;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Autowired
    private UserService userService;

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
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginContent))
                .andDo(print())
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse>() {
                });

        String at = ((LinkedHashMap<String, String>) resData.getData()).get("accessToken");
        String rt = ((LinkedHashMap<String, String>) resData.getData()).get("refreshToken");

        User loginUserByToken = userService.getLoginUserByToken(at);
        Assertions.assertThat(loginUserByToken.getUserId()).isEqualTo("abcd@gmail.com");
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

    @Test
    public void 로그아웃() throws Exception{
        String content = objectMapper.writeValueAsString(
                new UserSaveForm("abcd@gmail.com","testPass","testName")
        );
        mockMvc.perform(post("/user/signup")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        String loginContent = objectMapper.writeValueAsString(
                new UserLoginForm("abcd@gmail.com", "testPass")
        );
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginContent))
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse>() {
                });

        String at = ((LinkedHashMap<String, String>) resData.getData()).get("accessToken");
        String rt = ((LinkedHashMap<String, String>) resData.getData()).get("refreshToken");

        MvcResult logoutResult = mockMvc.perform(post("/user/logout")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginContent)
                        .header(Token.AT_HEADER, at)
                        .header(Token.RT_HEADER,rt))
                .andDo(print())
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk())
                .andReturn();


        org.junit.jupiter.api.Assertions.assertThrows(BadRequestException.class,
                () -> userService.getLoginUserByToken(at));
    }

    @Test
    public void 토큰_재발급() throws Exception{
        String content = objectMapper.writeValueAsString(
                new UserSaveForm("abcd@gmail.com","testPass","testName")
        );
        mockMvc.perform(post("/user/signup")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        String loginContent = objectMapper.writeValueAsString(
                new UserLoginForm("abcd@gmail.com", "testPass")
        );
        MvcResult mvcResult = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginContent))
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse>() {
                });

        String at = ((LinkedHashMap<String, String>) resData.getData()).get("accessToken");
        String rt = ((LinkedHashMap<String, String>) resData.getData()).get("refreshToken");

        MvcResult logoutResult = mockMvc.perform(get("/user/reissue")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginContent)
                        .header(Token.AT_HEADER, at)
                        .header(Token.RT_HEADER,rt))
                .andDo(print())
                .andExpect(jsonPath("success").value(true))
                .andExpect(status().isOk())
                .andReturn();

        org.junit.jupiter.api.Assertions.assertThrows(BadRequestException.class,
                () -> userService.getLoginUserByToken(at));

        ApiResponse resData2 = objectMapper.readValue(
                logoutResult.getResponse().getContentAsString(), new TypeReference<ApiResponse>() {
                });

        String at2 = ((LinkedHashMap<String, String>) resData2.getData()).get("accessToken");
        String rt2 = ((LinkedHashMap<String, String>) resData2.getData()).get("refreshToken");

        User loginUserByToken = userService.getLoginUserByToken(at2);
        Assertions.assertThat(loginUserByToken.getUserId()).isEqualTo("abcd@gmail.com");
    }
}
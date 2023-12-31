package com.springBoard.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostWriteForm;
import com.springBoard.token.model.Token;
import com.springBoard.user.model.TokenResponse;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String testToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOjE0MixcInVzZXJJZFwiOlwiYWJjZEBnbWFpbC5jb21cIixcInBhc3N3b3JkXCI6XCJ0ZXN0UGFzc1wiLFwidXNlck5hbWVcIjpcInRlc3ROYW1lXCIsXCJjRGF0ZVwiOjE2OTgwMDE0NDMwMDAsXCJsYXN0TG9naW5cIjoxNjk3OTY5MDQzMzk5LFwiaG9zdElwXCI6XCIxMjcuMC4wLjFcIixcImlzVXNlclwiOjF9IiwiZXhwIjoxNjk4MDU1NDQzfQ.h1Jx1MhbBWli-EZF7WNvyWyE272XZ3Zy9zYfrB0RmoY";

    @Test
    public void 유저전용게시판목록조회() throws Exception{

        TokenResponse tokenResponse = genLoginToken();
        mockMvc.perform(get("/board/userOnly/post")
                        .param("limit","10")
                        .param("offset","0")
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 유저전용게시판목록조회_실패() throws Exception{
        mockMvc.perform(get("/board/userOnly/post")
                        .param("limit","100")
                        .param("offset","20"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void 비회원전용게시판조회() throws Exception {
        mockMvc.perform(get("/board/notUserOnly/post")
                        .param("limit","10")
                        .param("offset","0"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void 존재하지않는_게시판조회() throws Exception{
        mockMvc.perform(get("/board/wrong/post")
                        .param("limit","100")
                        .param("offset","20"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 회원전용_게시글_작성() throws Exception {
        String writeContent = objectMapper.writeValueAsString(
                new PostWriteForm("테스트 제목", "테스트 내용")
        );
        mockMvc.perform(post("/board/userOnly/post")
                        .session(generateUserSession())
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 비회원전용_게시글_작성() throws Exception {
        String writeContent = objectMapper.writeValueAsString(
                new PostWriteForm("테스트 제목", "테스트 내용")
        );
        mockMvc.perform(post("/board/notUserOnly/post")
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    public void 게시글_조회_테스트() throws Exception{
        String writeContent = objectMapper.writeValueAsString(
                new PostWriteForm("테스트 제목", "테스트 내용")
        );
        MvcResult mvcResult = mockMvc.perform(post("/board/notUserOnly/post")
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse>() {
                });
        Post oriData = genPostFromHashMap((HashMap<Object, Object>) resData.getData());
        String rid = oriData.getRid();

        mockMvc.perform(get("/board/notUserOnly/post/" + rid)
//                        .session(generateUserSession())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/board/notUserOnly/post/" + rid)
//                        .session(generateUserSession())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public MockHttpSession generateUserSession() throws Exception{
        MockHttpSession session = new MockHttpSession();
        User user = new User.Builder()
                .id(1L)
                .userName("test")
                .password("testPass")
                .userId("testUserId")
                .hostIp("123.456.789")
                .isUser(1)
                .build();
        session.setAttribute("user",user);
        return session;
    }

    public MockHttpSession generateTmpSession() throws Exception{
        MockHttpSession session = new MockHttpSession();
        User user = new User.Builder()
                .userName("test")
                .password("testPass")
                .userId("testUserId")
                .hostIp("123.456.789")
                .isUser(0)
                .build();
        session.setAttribute("user",user);
        return session;
    }

    public Post genPostFromHashMap(HashMap<Object, Object> hm){
        return new Post.Builder()
                .id(new Long((Integer) hm.get("id")))
                .rid((String)hm.get("rid"))
                .build();
    }

    public TokenResponse genLoginToken() throws Exception{
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

        return new TokenResponse(at, rt);
    }
}
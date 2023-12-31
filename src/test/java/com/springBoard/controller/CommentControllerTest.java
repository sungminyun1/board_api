package com.springBoard.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.comment.model.Comment;
import com.springBoard.comment.model.CommentWriteForm;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.Post;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

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
class CommentControllerTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 댓글_목록_조회() throws Exception {
        TokenResponse tokenResponse = genLoginToken();
        mockMvc.perform(get("/board/userOnly/post/test/comment")
                        .param("limit","10")
                        .param("offset","0")
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 댓글_목록_조회_실패() throws Exception {
        TokenResponse tokenResponse = genLoginToken();
        mockMvc.perform(get("/board/userOnly/post/test33/comment")
                        .param("limit","10")
                        .param("offset","0")
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 댓글_작성() throws Exception{
        String writeContent = objectMapper.writeValueAsString(
                new CommentWriteForm("테스트 댓글")
        );

        TokenResponse tokenResponse = genLoginToken();
        mockMvc.perform(post("/board/userOnly/post/test/comment")
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 댓글_수정() throws Exception{
        String writeContent = objectMapper.writeValueAsString(
                new CommentWriteForm("테스트 댓글")
        );

        TokenResponse tokenResponse = genLoginToken();
        MvcResult mvcResult = mockMvc.perform(post("/board/userOnly/post/test/comment")
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse>() {
                });

        Comment oriData = genCommentFromHashMap((HashMap<Object, Object>) resData.getData());
        String rid = oriData.getRid();

        String writeContent2 = objectMapper.writeValueAsString(
                new CommentWriteForm("테스트 댓글 수정버전")
        );

        mockMvc.perform(put("/board/userOnly/post/test/comment/" + rid)
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                        .content(writeContent2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 댓글_삭제() throws  Exception{
        String writeContent = objectMapper.writeValueAsString(
                new CommentWriteForm("테스트 댓글")
        );

        TokenResponse tokenResponse = genLoginToken();
        MvcResult mvcResult = mockMvc.perform(post("/board/userOnly/post/test/comment")
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponse>() {
                });

        Comment oriData = genCommentFromHashMap((HashMap<Object, Object>) resData.getData());
        String rid = oriData.getRid();

        mockMvc.perform(delete("/board/userOnly/post/test/comment/" + rid)
                        .header(Token.AT_HEADER, tokenResponse.getAccessToken())
                        .header(Token.RT_HEADER, tokenResponse.getRefreshToken())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public Comment genCommentFromHashMap(HashMap<Object, Object> hm){
        return new Comment.Builder()
                .id(new Long((Integer) hm.get("id")))
                .rid((String)hm.get("rid"))
                .build();
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
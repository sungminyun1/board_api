package com.springBoard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.post.model.PostWriteForm;
import com.springBoard.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 유저전용게시판목록조회() throws Exception{

        mockMvc.perform(get("/board/userOnly/post")
                        .param("limit","10")
                        .param("offset","0")
                        .session(generateSession()))
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
    public void 존재하지않는_게시판조회() throws Exception{
        mockMvc.perform(get("/board/wrong/post")
                        .param("limit","100")
                        .param("offset","20"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 게시글_작성() throws Exception {
        String writeContent = objectMapper.writeValueAsString(
                new PostWriteForm("테스트 제목", "테스트 내용")
        );
        mockMvc.perform(post("/board/userOnly/post")
                .session(generateSession())
                .content(writeContent)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    public MockHttpSession generateSession() throws Exception{
        MockHttpSession session = new MockHttpSession();
        String userSession = objectMapper.writeValueAsString(
                new User.Builder().userName("test").password("testPass").build()
        );
        session.setAttribute("user",userSession);
        return session;
    }
}
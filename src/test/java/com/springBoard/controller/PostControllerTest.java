package com.springBoard.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.payload.ApiResponseWithData;
import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostWriteForm;
import com.springBoard.user.model.User;
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

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Test
    public void 유저전용게시판목록조회() throws Exception{

        mockMvc.perform(get("/board/userOnly/post")
                        .param("limit","10")
                        .param("offset","0")
                        .session(generateUserSession()))
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
    public void 유저전용게시판목록조회_실패2() throws Exception{

        mockMvc.perform(get("/board/userOnly/post")
                        .param("limit","10")
                        .param("offset","0")
                        .session(generateTmpSession()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 비회원전용게시판조회() throws Exception {
        mockMvc.perform(get("/board/notUserOnly/post")
                        .param("limit","10")
                        .param("offset","0")
                        .session(generateTmpSession()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 비회원전용게시판조회2() throws Exception {
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
//                        .session(generateUserSession())
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void 비회원전용_게시글_수정() throws Exception {
        String writeContent = objectMapper.writeValueAsString(
                new PostWriteForm("테스트 제목", "테스트 내용")
        );
        MvcResult mvcResult = mockMvc.perform(post("/board/notUserOnly/post")
//                        .session(generateUserSession())
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ApiResponseWithData<Post> resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponseWithData<Post>>() {
                });

        Post oriData = resData.getData();
        String rid = oriData.getRid();
        String writeContent2 = objectMapper.writeValueAsString(
                new PostWriteForm("테스트 제목 수정", "테스트 내용 수정")
        );

        mockMvc.perform(put("/board/notUserOnly/post/" + rid)
//                        .session(generateUserSession())
                        .content(writeContent2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 비회원전용_게시글_삭제() throws Exception {
        String writeContent = objectMapper.writeValueAsString(
                new PostWriteForm("테스트 제목", "테스트 내용")
        );
        MvcResult mvcResult = mockMvc.perform(post("/board/notUserOnly/post")
//                        .session(generateUserSession())
                        .content(writeContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ApiResponseWithData<Post> resData = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResponseWithData<Post>>() {
                });

        Post oriData = resData.getData();
        String rid = oriData.getRid();

        mockMvc.perform(delete("/board/notUserOnly/post/" + rid)
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


}
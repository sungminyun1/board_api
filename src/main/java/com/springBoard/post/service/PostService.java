package com.springBoard.post.service;

import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.PostWriteForm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface PostService {
    ApiResponse getPostList(String boardUrl, Integer limit, Integer offset);

    ApiResponse writePost(String boardUrl, PostWriteForm postWriteForm, HttpServletRequest request);

    ApiResponse updatePost(String boardUrl, String postRid, PostWriteForm postWriteForm, HttpServletRequest request);

    ApiResponse deletePost(String boardUrl, String postRid, HttpServletRequest request);
}

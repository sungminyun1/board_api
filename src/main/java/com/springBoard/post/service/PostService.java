package com.springBoard.post.service;

import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostWriteForm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService {
    List<Post> getPostList(String boardUrl, Integer limit, Integer offset);

    Post writePost(String boardUrl, PostWriteForm postWriteForm, HttpServletRequest request);

    Post updatePost(String boardUrl, String postRid, PostWriteForm postWriteForm, HttpServletRequest request);

    void deletePost(String boardUrl, String postRid, HttpServletRequest request);
}

package com.springBoard.post.service;

import com.springBoard.payload.ApiResponse;
import org.springframework.stereotype.Service;

public interface PostService {
    ApiResponse getPostList(String boardUrl, Integer limit, Integer offset);
}

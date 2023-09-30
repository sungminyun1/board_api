package com.springBoard.post.service.impl;

import com.springBoard.board.model.Board;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostSearchCond;
import com.springBoard.post.repository.PostRepository;
import com.springBoard.post.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public ApiResponse getPostList(String boardUrl, Integer limit, Integer offset) {
        Board board = Board.boardUrlMap.get("/" + boardUrl);
        if(board == null){
            ApiResponse apiResponse = new ApiResponse(false, "존재하지 않는 boardurl입니다. " + boardUrl);
            throw new BadRequestException(apiResponse);
        }

        PostSearchCond postSearchCond = new PostSearchCond.Builder()
                .boardId(board.getId())
                .limit(limit)
                .offset(offset)
                .build();

        List<Post> postList = postRepository.findList(postSearchCond);
        return new ApiResponse<Post>(true,"게시물 조회 성공", postList);
    }
}

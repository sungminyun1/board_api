package com.springBoard.post.service.impl;

import com.springBoard.board.model.Board;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.payload.ApiResponseWithData;
import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostPermission;
import com.springBoard.post.model.PostSearchCond;
import com.springBoard.post.model.PostWriteForm;
import com.springBoard.post.repository.PostRepository;
import com.springBoard.post.service.PostService;
import com.springBoard.user.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

        PostSearchCond postSearchCond = new PostSearchCond.Builder()
                .boardId(board.getId())
                .limit(limit)
                .offset(offset)
                .build();

        List<Post> postList = postRepository.findList(postSearchCond);
        return new ApiResponseWithData<Post>(true,"게시물 조회 성공", postList);
    }

    @Override
    public ApiResponse writePost(String boardUrl, PostWriteForm postWriteForm, HttpServletRequest request) {
        Board board = Board.boardUrlMap.get("/" + boardUrl);

        Long userId;

        if(board.getPermission().equals(PostPermission.USER)){
            User user = (User)request.getSession().getAttribute("user");
            userId = user.getId();
        }else{
            userId = 1L;
        }

        Post post = new Post.Builder()
                .title(postWriteForm.getTitle())
                .text(postWriteForm.getContent())
                .boardId(board.getId())
                .userId(userId)
                .build();
        return null;
    }
}

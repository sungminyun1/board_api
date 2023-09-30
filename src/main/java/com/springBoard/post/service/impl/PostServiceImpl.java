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
import com.springBoard.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

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

        User sessionUser = (User)request.getSession().getAttribute("user");

        Post post = new Post.Builder()
                .rid(Util.generateRid())
                .boardId(board.getId())
                .userId(sessionUser.getId())
                .commentCount(0L)
                .readCount(0L)
                .cDate(new Date())
                .title(postWriteForm.getTitle())
                .text(postWriteForm.getContent())
                .build();

        postRepository.save(post);
        return new ApiResponseWithData<Post>(true,"게시물 작성 성공",post);
    }
}

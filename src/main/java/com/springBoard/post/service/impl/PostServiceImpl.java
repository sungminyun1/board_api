package com.springBoard.post.service.impl;

import com.springBoard.board.model.Board;
import com.springBoard.exception.AccessDeniedException;
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
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getPostList(String boardUrl, Integer limit, Integer offset) {
        Board board = Board.boardUrlMap.get("/" + boardUrl);

        PostSearchCond postSearchCond = new PostSearchCond.Builder()
                .boardId(board.getId())
                .limit(limit)
                .offset(offset)
                .build();

        return postRepository.findList(postSearchCond);
//        return new ApiResponseWithData<Post>(true,"게시물 조회 성공", postList);
    }

    @Override
    public Post writePost(String boardUrl, PostWriteForm postWriteForm, HttpServletRequest request) {
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
        return post;
//        return new ApiResponseWithData<Post>(true,"게시물 작성 성공",post);
    }

    @Override
    public Post updatePost(String boardUrl, String postRid, PostWriteForm postWriteForm, HttpServletRequest request) {
        Board board = Board.boardUrlMap.get("/" + boardUrl);

        User sessionUser = (User)request.getSession().getAttribute("user");

        PostSearchCond postSearchCond = new PostSearchCond.Builder().rid(postRid).build();
        Optional<Post> targetPost = postRepository.find(postSearchCond);
        if(targetPost.isEmpty()){
            ApiResponse apiResponse = new ApiResponse(false, "존재하지 않는 게시글입니다.");
            throw new BadRequestException(apiResponse);
        }

        if(!targetPost.get().getUserId().equals(sessionUser.getId())){
            ApiResponse apiResponse = new ApiResponse(false, "수정 권한이 없습니다.");
            throw new AccessDeniedException(apiResponse);
        }

        targetPost.get().setuDate(new Date());
        targetPost.get().setTitle(postWriteForm.getTitle());
        targetPost.get().setText(postWriteForm.getContent());

        postRepository.updateById(targetPost.get());

        return targetPost.get();
    }

    @Override
    public void deletePost(String boardUrl, String postRid, HttpServletRequest request) {
        Board board = Board.boardUrlMap.get("/" + boardUrl);

        User sessionUser = (User)request.getSession().getAttribute("user");

        PostSearchCond postSearchCond = new PostSearchCond.Builder().rid(postRid).build();
        Optional<Post> targetPost = postRepository.find(postSearchCond);
        if(targetPost.isEmpty()){
            ApiResponse apiResponse = new ApiResponse(false, "존재하지 않는 게시글입니다.");
            throw new BadRequestException(apiResponse);
        }

        if(!targetPost.get().getUserId().equals(sessionUser.getId())){
            ApiResponse apiResponse = new ApiResponse(false, "삭제 권한이 없습니다.");
            throw new AccessDeniedException(apiResponse);
        }
        
        postRepository.deleteById(targetPost.get().getId());
    }
}

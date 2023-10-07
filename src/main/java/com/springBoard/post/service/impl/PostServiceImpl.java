package com.springBoard.post.service.impl;

import com.springBoard.board.model.Board;
import com.springBoard.constant.ResponseStatus;
import com.springBoard.exception.AccessDeniedException;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
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
    }

    @Override
    public Post updatePost(String boardUrl, String postRid, PostWriteForm postWriteForm, HttpServletRequest request) {
        Board board = Board.boardUrlMap.get("/" + boardUrl);

        User sessionUser = (User)request.getSession().getAttribute("user");

        PostSearchCond postSearchCond = new PostSearchCond.Builder().rid(postRid).build();
        Optional<Post> targetPost = postRepository.find(postSearchCond);
        if(targetPost.isEmpty()){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.POST_NOT_EXIST);
            throw new BadRequestException(apiResponse);
        }

        if(!targetPost.get().getUserId().equals(sessionUser.getId())){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.MEMBER_ACCESS_DENIED);
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
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.POST_NOT_EXIST);
            throw new BadRequestException(apiResponse);
        }

        if(!targetPost.get().getUserId().equals(sessionUser.getId())){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.MEMBER_ACCESS_DENIED);
            throw new AccessDeniedException(apiResponse);
        }
        
        postRepository.deleteById(targetPost.get().getId());
    }

    @Override
    public Post readPost(String boardUrl, String postRid, HttpServletRequest request) {

        User sessionUser = (User)request.getSession().getAttribute("user");

        PostSearchCond postSearchCond = new PostSearchCond.Builder().rid(postRid).build();
        Optional<Post> targetPostOp = postRepository.find(postSearchCond);
        if(targetPostOp.isEmpty()){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.POST_NOT_EXIST);
            throw new BadRequestException(apiResponse);
        }
        Post targetPost = targetPostOp.get();
        Integer userPostReadCount = postRepository.checkUserPostView(sessionUser.getId(), targetPost.getId());
        if(userPostReadCount == 0){
            postRepository.insertUserPostView(sessionUser.getId(), targetPost.getId());
            targetPost.setReadCount(targetPost.getReadCount() +1);
            postRepository.updateById(targetPost);
        }

        return targetPost;
    }

    @Override
    public Post getPostByRid(String rid) {
        PostSearchCond postSearchCond = new PostSearchCond.Builder().rid(rid).build();
        Optional<Post> targetPostOp = postRepository.find(postSearchCond);
        if(targetPostOp.isEmpty()){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.POST_NOT_EXIST);
            throw new BadRequestException(apiResponse);
        }
        return targetPostOp.get();
    }

}

package com.springBoard.post.service.impl;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.model.CommentWriteForm;
import com.springBoard.comment.service.CommentService;
import com.springBoard.post.model.Post;
import com.springBoard.post.service.PostCommentCompositeService;
import com.springBoard.post.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public class PostCommentCompositeServiceImpl implements PostCommentCompositeService {
    private final PostService postService;
    private final CommentService commentService;

    public PostCommentCompositeServiceImpl(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Override
    public List<Comment> getCommentList(String postRid, Integer limit, Integer offset) {
        Post post = postService.getPostByRid(postRid);
        return commentService.getCommentList(post.getId(),limit,offset);
    }

    @Override
    public Comment writeComment(String postRid, CommentWriteForm commentWriteForm, HttpServletRequest request) {
        Post post = postService.getPostByRid(postRid);
        post.setCommentCount(post.getCommentCount() +1);
        return commentService.writeComment(post.getId(), commentWriteForm, request);
    }
}

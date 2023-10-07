package com.springBoard.comment.service;

import com.springBoard.comment.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentList(Long postId, Integer limit, Integer offset);
}

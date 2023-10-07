package com.springBoard.comment.repository;

import com.springBoard.comment.model.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findList(Long postId, Integer limit, Integer offset);
}

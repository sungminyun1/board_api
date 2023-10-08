package com.springBoard.comment.repository;

import com.springBoard.comment.model.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> findList(Long postId, Integer limit, Integer offset);

    void save(Comment comment);

    Comment findByRid(String rid);

    void updateById(Comment comment);

    void deleteById(Long id);
}

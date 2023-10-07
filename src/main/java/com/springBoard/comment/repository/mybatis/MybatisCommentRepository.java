package com.springBoard.comment.repository.mybatis;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisCommentRepository implements CommentRepository {
    private final CommentMapper commentMapper;

    public MybatisCommentRepository(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> findList(Long postId, Integer limit, Integer offset) {
        return commentMapper.findList(postId, limit, offset);
    }
}

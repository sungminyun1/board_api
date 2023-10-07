package com.springBoard.comment.repository.mybatis;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisCommentRepository implements CommentRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final CommentMapper commentMapper;

    public MybatisCommentRepository(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> findList(Long postId, Integer limit, Integer offset) {
        return commentMapper.findList(postId, limit, offset);
    }

    @Override
    public void save(Comment comment) {
        log.info("here i am comment repo {}", comment);
        commentMapper.save(comment);
    }
}

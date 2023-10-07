package com.springBoard.comment.service.impl;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.repository.CommentRepository;
import com.springBoard.comment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentList(Long postId, Integer limit, Integer offset) {
        return commentRepository.findList(postId, limit, offset);
    }
}

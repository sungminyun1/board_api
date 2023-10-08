package com.springBoard.comment.service.impl;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.model.CommentWriteForm;
import com.springBoard.comment.repository.CommentRepository;
import com.springBoard.comment.service.CommentService;
import com.springBoard.constant.ResponseStatus;
import com.springBoard.exception.AccessDeniedException;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
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

    @Override
    public Comment writeComment(Long postId, CommentWriteForm commentWriteForm, HttpServletRequest request) {
        User sessionUser = (User)request.getSession().getAttribute("user");

        Comment comment = new Comment.Builder()
                .rid(Util.generateRid())
                .postId(postId)
                .userId(sessionUser.getId())
                .text(commentWriteForm.getContent())
                .cDate(new Date())
                .build();

        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Comment updateComment(String commentRid, CommentWriteForm commentWriteForm, HttpServletRequest request) {
        User sessionUser = (User)request.getSession().getAttribute("user");

        Comment comment = commentRepository.findByRid(commentRid);
        if(comment == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.COMMENT_NOT_EXIST);
            throw new BadRequestException(apiResponse);
        }

        if(comment.getUserId() != sessionUser.getId()){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.MEMBER_ACCESS_DENIED);
            throw new AccessDeniedException(apiResponse);
        }

        comment.setText(commentWriteForm.getContent());
        comment.setuDate(new Date());

        commentRepository.updateById(comment);

        return comment;
    }
}

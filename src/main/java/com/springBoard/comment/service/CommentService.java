package com.springBoard.comment.service;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.model.CommentWriteForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {
    List<Comment> getCommentList(Long postId, Integer limit, Integer offset);

    Comment writeComment(Long postId, CommentWriteForm commentWriteForm, HttpServletRequest request);

    Comment updateComment(String commentRid, CommentWriteForm commentWriteForm, HttpServletRequest request);
}

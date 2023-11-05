package com.springBoard.post.service;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.model.CommentWriteForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostCommentCompositeService {
    List<Comment> getCommentList(String postRid, Integer limit, Integer offset);

    Comment writeComment(String postRid, CommentWriteForm commentWriteForm, HttpServletRequest request);
}

package com.springBoard.post.service;

import com.springBoard.comment.model.Comment;

import java.util.List;

public interface PostCommentCompositeService {
    List<Comment> getCommentList(String postRid, Integer limit, Integer offset);
}

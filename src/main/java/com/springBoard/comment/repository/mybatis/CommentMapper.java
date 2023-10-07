package com.springBoard.comment.repository.mybatis;

import com.springBoard.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findList(@Param("postId") Long postId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    void save(Comment comment);
}

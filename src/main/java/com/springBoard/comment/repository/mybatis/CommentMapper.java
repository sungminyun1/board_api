package com.springBoard.comment.repository.mybatis;

import com.springBoard.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    List<Comment> findList(@Param("postId") Long postId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    void save(Comment comment);

    Optional<Comment> findByRid(String rid);

    void updateById(Comment comment);
}

package com.springBoard.post.repository.mybatis;

import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostSearchCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    List<Post> findList(PostSearchCond postSearchCond);

    void save(Post post);

    Optional<Post> find(PostSearchCond postSearchCond);

    void updateById(Post post);

    void deleteById(@Param("id") Long id);
}

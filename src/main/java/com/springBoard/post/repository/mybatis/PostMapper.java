package com.springBoard.post.repository.mybatis;

import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostSearchCond;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> findList(PostSearchCond postSearchCond);
}

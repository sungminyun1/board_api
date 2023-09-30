package com.springBoard.post.repository;

import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostSearchCond;
import com.springBoard.post.model.PostWriteForm;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findList(PostSearchCond postSearchCond);

    void save(Post post);

    Optional<Post> find(PostSearchCond postSearchCond);

    void updateById(Post post);

    void deleteById(Long id);
}

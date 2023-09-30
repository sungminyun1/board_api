package com.springBoard.post.repository;

import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostSearchCond;
import com.springBoard.post.model.PostWriteForm;

import java.util.List;

public interface PostRepository {
    List<Post> findList(PostSearchCond postSearchCond);

    void save(Post post);
}

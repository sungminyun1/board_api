package com.springBoard.post.repository.mybatis;

import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostSearchCond;
import com.springBoard.post.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyBatisPostRepository implements PostRepository {
    private final PostMapper postMapper;

    public MyBatisPostRepository(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<Post> findList(PostSearchCond postSearchCond) {return postMapper.findList(postSearchCond);}

    @Override
    public void save(Post post) {
        postMapper.save(post);
    }
}

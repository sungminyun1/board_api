package com.springBoard.post.repository.mybatis;

import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostSearchCond;
import com.springBoard.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MyBatisPostRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시글_목록_조회(){
        PostSearchCond postSearchCond = new PostSearchCond.Builder()
                .limit(10)
                .offset(0)
                .boardId(1)
                .build();
        List<Post> posts = postRepository.findList(postSearchCond);
        for(Post p : posts){
            log.info(p.toString());
        }
    }
}
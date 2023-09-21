package com.springBoard.user.repository.mybatis;

import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyBatisUserRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    UserRepository userRepository;

    @Test
    public void save(){
        UserSaveForm userSaveForm = new UserSaveForm("userId2","pass","userName");
        User user = userRepository.save(userSaveForm);
        log.info("here i am user {}", user);
    }

    @Test
    public void findAll(){
        List<User> users = userRepository.findAll();
        for (User u : users) {
            log.info(u.toString());
        }
    }
}
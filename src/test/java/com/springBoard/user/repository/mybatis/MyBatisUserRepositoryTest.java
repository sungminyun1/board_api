package com.springBoard.user.repository.mybatis;

import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.model.UserSearchCond;
import com.springBoard.user.model.UserUpdateDto;
import com.springBoard.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MyBatisUserRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    UserRepository userRepository;

    @Test
    public void save(){
        User user = new User.Builder()
                .isUser(1)
                .userName("test name")
                .userId("test id")
                .hostIp("123.456.789")
                .password("test pass")
                .build();

        userRepository.save(user);

        UserSearchCond userSearchCond = new UserSearchCond();
        userSearchCond.setId(user.getId());
        User foundUser = userRepository.find(userSearchCond).get();

        assertThat(user.getId()).isEqualTo(foundUser.getId());
        assertThat(user.getUserId()).isEqualTo(foundUser.getUserId());
        assertThat(user.getUserName()).isEqualTo(foundUser.getUserName());
    }


    @Test
    public void findAll(){
        List<User> users = userRepository.findAll();
        for (User u : users) {
            log.info(u.toString());
        }
    }

//    @Test
//    public void update(){
//        UserSaveForm userSaveForm = new UserSaveForm("userId","pass","userName");
//        User user = userRepository.save(userSaveForm);
//
//        Date now = new Date();
//        UserUpdateDto userUpdateDto = new UserUpdateDto(user.getPassword(),user.getUserName(),now);
//        user = userRepository.updateById(user.getId(), userUpdateDto).get();
//        assertThat(user.getLastLogin().getTime() / 1000).isEqualTo(now.getTime() / 1000);
//    }
}
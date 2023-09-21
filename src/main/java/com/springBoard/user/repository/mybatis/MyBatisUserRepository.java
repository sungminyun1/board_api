package com.springBoard.user.repository.mybatis;

import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyBatisUserRepository implements UserRepository {

    private final UserMapper userMapper;

    public MyBatisUserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User save(UserSaveForm userSaveForm) {
        User user = new User();
        user.setUserName(userSaveForm.getUserName());
        user.setUserId(userSaveForm.getUserId());
        user.setPassword(userSaveForm.getPassword());
        userMapper.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}

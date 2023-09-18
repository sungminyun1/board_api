package com.springBoard.config;

import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.repository.mybatis.MyBatisUserRepository;
import com.springBoard.user.repository.mybatis.UserMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    private final UserMapper userMapper;

    public MyBatisConfig(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserRepository userRepository(){
        return new MyBatisUserRepository(userMapper);
    }
}

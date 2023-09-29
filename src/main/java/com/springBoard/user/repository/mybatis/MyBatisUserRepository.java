package com.springBoard.user.repository.mybatis;

import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.model.UserSearchCond;
import com.springBoard.user.model.UserUpdateDto;
import com.springBoard.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MyBatisUserRepository implements UserRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final UserMapper userMapper;

    public MyBatisUserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User save(UserSaveForm userSaveForm) {
        User user = new User.Builder()
                .userId(userSaveForm.getUserId())
                .userName(userSaveForm.getUserName())
                .password(userSaveForm.getPassword())
                .build();

        userMapper.save(user);
        return user;
    }

    @Override
    public Optional<User> updateById(Long id, UserUpdateDto userUpdateDto) {
        userMapper.updateById(id,userUpdateDto);
        UserSearchCond userSearchCond = new UserSearchCond();
        userSearchCond.setId(id);
        return find(userSearchCond);
    }

    @Override
    public Optional<User> find(UserSearchCond userSearchCond) {
        return userMapper.find(userSearchCond);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}

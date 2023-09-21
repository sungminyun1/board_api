package com.springBoard.user.repository.mybatis;

import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.model.UserSearchCond;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    void save(User user);

    Optional<User> find(UserSearchCond userSearchCond);

    List<User> findAll();
}

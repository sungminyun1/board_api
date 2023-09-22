package com.springBoard.user.repository;

import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.model.UserSearchCond;
import com.springBoard.user.model.UserUpdateDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(UserSaveForm userSaveForm);

    Optional<User> find(UserSearchCond userSearchCond);

    List<User> findAll();

    Optional<User> updateById(Long id, UserUpdateDto userUpdateDto);

}

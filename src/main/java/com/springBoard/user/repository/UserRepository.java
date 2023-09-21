package com.springBoard.user.repository;

import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;

import java.util.List;

public interface UserRepository {
    User save(UserSaveForm userSaveForm);
    List<User> findAll();
}

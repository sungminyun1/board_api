package com.springBoard.user.repository;

import com.springBoard.user.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}

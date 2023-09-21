package com.springBoard.user.service.impl;

import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ApiResponse addUser(UserSaveForm userSaveForm) {
        User user = userRepository.save(userSaveForm);
        return null;
    }
}

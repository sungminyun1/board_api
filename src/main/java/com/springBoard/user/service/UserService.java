package com.springBoard.user.service;

import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.UserSaveForm;

public interface UserService {
    ApiResponse addUser(UserSaveForm userSaveForm);
}

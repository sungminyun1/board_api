package com.springBoard.user.service;

import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    ApiResponse addUser(UserSaveForm userSaveForm);

    ApiResponse login(UserLoginForm userLoginForm, HttpServletRequest request);
}

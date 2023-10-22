package com.springBoard.user.service;

import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.TokenData;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    User addUser(UserSaveForm userSaveForm, HttpServletRequest request);

    TokenData login(UserLoginForm userLoginForm, HttpServletRequest request);

    void logout(HttpServletRequest request);
}

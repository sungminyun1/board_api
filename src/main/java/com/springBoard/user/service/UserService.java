package com.springBoard.user.service;

import com.springBoard.user.model.TokenResponse;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    User addUser(UserSaveForm userSaveForm, HttpServletRequest request);

    TokenResponse login(UserLoginForm userLoginForm, HttpServletRequest request);

    TokenResponse reissue(HttpServletRequest request);

    void logout(HttpServletRequest request);

    User getLoginUserByToken(String token);
}

package com.springBoard.controller;

import com.springBoard.aop.LoginCheck;
import com.springBoard.token.model.Token;
import com.springBoard.user.model.TokenResponse;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody @Validated UserSaveForm userSaveForm, HttpServletRequest request){
        return userService.addUser(userSaveForm, request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Validated UserLoginForm userLoginForm, HttpServletRequest request){
        return userService.login(userLoginForm, request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request){
        userService.logout(request);
    }

    @GetMapping("/reissue")
    public TokenResponse reissue(HttpServletRequest request){
        return userService.reissue(request);
    };
}

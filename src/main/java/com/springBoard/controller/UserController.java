package com.springBoard.controller;

import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserLoginForm;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void login(@RequestBody @Validated UserLoginForm userLoginForm, HttpServletRequest request){
        userService.login(userLoginForm, request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request){
        userService.logout(request);
    }
}

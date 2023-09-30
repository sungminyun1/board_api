package com.springBoard.controller;

import com.springBoard.payload.ApiResponse;
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
    public ResponseEntity<ApiResponse> signup(@RequestBody @Validated UserSaveForm userSaveForm, HttpServletRequest request){
        ApiResponse apiResponse = userService.addUser(userSaveForm, request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Validated UserLoginForm userLoginForm, HttpServletRequest request){
        ApiResponse apiResponse = userService.login(userLoginForm, request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request){
        ApiResponse apiResponse = userService.logout(request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

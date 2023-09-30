package com.springBoard.user.service.impl;

import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;


    @Test
    public void addUser(){
        UserSaveForm userSaveForm = new UserSaveForm();
        userSaveForm.setUserId("userId");
        userSaveForm.setUserName("userName");
        userSaveForm.setPassword("password");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("X-FORWARDED-FOR","127.0.0.1");

        ApiResponse apiResponse = userService.addUser(userSaveForm,mockRequest);
        assertThat(apiResponse.getSuccess()).isTrue();

    }

    @Test
    public void 중복_회원_예외(){
        UserSaveForm userSaveForm = new UserSaveForm();
        userSaveForm.setUserId("userId");
        userSaveForm.setUserName("userName");
        userSaveForm.setPassword("password");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("X-FORWARDED-FOR","127.0.0.1");

        ApiResponse apiResponse = userService.addUser(userSaveForm,mockRequest);
        Assertions.assertThrows(BadRequestException.class, ()->{
            userService.addUser(userSaveForm,mockRequest);
        });
    }
}
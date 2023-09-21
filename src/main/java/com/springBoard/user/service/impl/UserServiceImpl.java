package com.springBoard.user.service.impl;

import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSaveForm;
import com.springBoard.user.model.UserSearchCond;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ApiResponse addUser(UserSaveForm userSaveForm) {
        // userId 중복 검사
        UserSearchCond userDupCheck = new UserSearchCond();
        userDupCheck.setUserId(userSaveForm.getUserId());
        Optional<User> dupUser = userRepository.find(userDupCheck);
        if(dupUser.isPresent()){
            ApiResponse apiResponse = new ApiResponse(false, "중복된 userId가 존재 합니다.");
            throw new BadRequestException(apiResponse);
        }

        User user = userRepository.save(userSaveForm);
        return new ApiResponse(true, "회원 가입 완료");
    }
}

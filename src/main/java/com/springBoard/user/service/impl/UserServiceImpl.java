package com.springBoard.user.service.impl;

import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.*;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

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

    @Override
    @Transactional
    public ApiResponse login(UserLoginForm userLoginForm, HttpServletRequest request) {
        User loginUser = getLoginUser(userLoginForm);

        if(loginUser == null){
            ApiResponse apiResponse = new ApiResponse(false,"아이디 또는 비밀번호가 맞지 않습니다.");
            throw new BadRequestException(apiResponse);
        }

        UserUpdateDto userUpdateDto = new UserUpdateDto(loginUser.getPassword(), loginUser.getUserName(), new Date());
        loginUser = userRepository.updateById(loginUser.getId(), userUpdateDto).get();

        HttpSession session = request.getSession();
        session.setAttribute("user", loginUser);

        return new ApiResponse(true, "로그인 성공");
    }

    public User getLoginUser(UserLoginForm userLoginForm){
        UserSearchCond userSearchCond = new UserSearchCond();
        userSearchCond.setUserId(userLoginForm.getUserId());

        return userRepository.find(userSearchCond)
                .filter((m) -> m.getPassword().equals(userLoginForm.getPassword()))
                .orElse(null);
    }

    @Override
    public ApiResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return new ApiResponse(true, "로그아웃 성공");
    }
}

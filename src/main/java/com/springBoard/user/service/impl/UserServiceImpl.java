package com.springBoard.user.service.impl;

import com.springBoard.constant.ResponseStatus;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.user.model.*;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import com.springBoard.util.TokenManager;
import com.springBoard.util.Util;
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
    public User addUser(UserSaveForm userSaveForm, HttpServletRequest request) {
        // userId 중복 검사
        UserSearchCond userDupCheck = new UserSearchCond();
        userDupCheck.setUserId(userSaveForm.getUserId());
        Optional<User> dupUser = userRepository.find(userDupCheck);
        if (dupUser.isPresent()) {
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.MEMBER_ID_DUPLICATED);
            throw new BadRequestException(apiResponse);
        }
        User user = new User.Builder()
                .isUser(1)
                .hostIp(Util.getClientIp(request))
                .userId(userSaveForm.getUserId())
                .userName(userSaveForm.getUserName())
                .password(userSaveForm.getPassword())
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public TokenData login(UserLoginForm userLoginForm, HttpServletRequest request) {
        User loginUser = getLoginUser(userLoginForm);

        if(loginUser == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.MEMBER_ID_PW_MISMATCH);
            throw new BadRequestException(apiResponse);
        }

        loginUser.setLastLogin(new Date());
        userRepository.updateById(loginUser);

        String token = TokenManager.createToken(loginUser);
        return new TokenData(token);
    }

    public User getLoginUser(UserLoginForm userLoginForm){
        UserSearchCond userSearchCond = new UserSearchCond();
        userSearchCond.setUserId(userLoginForm.getUserId());

        return userRepository.find(userSearchCond)
                .filter((m) -> m.getPassword().equals(userLoginForm.getPassword()))
                .orElse(null);
    }

    @Override
    public void logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
    }
}

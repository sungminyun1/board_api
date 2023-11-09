package com.springBoard.user.service.impl;

import com.springBoard.constant.ResponseStatus;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.token.model.Token;
import com.springBoard.token.repository.TokenRepository;
import com.springBoard.user.model.*;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import com.springBoard.util.TokenManager;
import com.springBoard.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
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
    public TokenResponse login(UserLoginForm userLoginForm, HttpServletRequest request) {
        User loginUser = getLoginUser(userLoginForm);

        if(loginUser == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.MEMBER_ID_PW_MISMATCH);
            throw new BadRequestException(apiResponse);
        }

        loginUser.setLastLogin(new Date());
        userRepository.updateById(loginUser);

        Token token = genUserToken(loginUser);

        tokenRepository.save(token);

        return new TokenResponse(token.getAccessToken(), token.getRefreshToken());
    }

    @Override
    @Transactional
    public TokenResponse reissue(HttpServletRequest request) {
        String at = request.getHeader(Token.AT_HEADER);
        String rt = request.getHeader(Token.RT_HEADER);

        Token byAT = tokenRepository.findByAT(at).orElse(null);
        if(byAT == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.TOKEN_USER_NOT_FOUND);
            throw new BadRequestException(apiResponse);
        }

        if (!rt.equals(byAT.getRefreshToken())) {
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.INVALID_REFRESH_TOKEN);
            throw new BadRequestException(apiResponse);
        }

        UserSearchCond userSearchCond = new UserSearchCond.Builder()
                .id(byAT.getUserId())
                .build();
        User loginUser = userRepository.find(userSearchCond).orElse(null);
        if(loginUser == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.TOKEN_USER_NOT_FOUND);
            throw new BadRequestException(apiResponse);
        }

        Token token = genUserToken(loginUser);

        tokenRepository.save(token);
        tokenRepository.deleteByAT(at);

        return new TokenResponse(token.getAccessToken(), token.getRefreshToken());
    }

    public Token genUserToken(User user){
        String accessToken = Util.generateRid();
        String refreshToken = Util.generateRid();

        return new Token.Builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .accessTokenExpire(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .refreshToken(refreshToken)
                .refreshTokenExpire(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
                .build();
    }

    public User getLoginUser(UserLoginForm userLoginForm){
        UserSearchCond userSearchCond = new UserSearchCond();
        userSearchCond.setUserId(userLoginForm.getUserId());

        return userRepository.find(userSearchCond)
                .filter((m) -> m.getPassword().equals(userLoginForm.getPassword()))
                .orElse(null);
    }

    @Transactional
    @Override
    public User getLoginUserByToken(String token) {
        Token loginToken = tokenRepository.findByAT(token).orElse(null);

        if(loginToken == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.TOKEN_USER_NOT_FOUND);
            throw new BadRequestException(apiResponse);
        }

        if(loginToken.getAccessTokenExpire().after(new Date())){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.TOKEN_EXPIRED);
            throw new BadRequestException(apiResponse);
        }

        UserSearchCond userSearchCond = new UserSearchCond();
        userSearchCond.setId(loginToken.getUserId());

        return userRepository.find(userSearchCond)
                .orElse(null);
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = request.getHeader(Token.AT_HEADER);
        if(token == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.TOKEN_NOT_FOUND);
            throw new BadRequestException(apiResponse);
        }
        tokenRepository.deleteByAT(token);
    }

}

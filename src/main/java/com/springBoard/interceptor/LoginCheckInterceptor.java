package com.springBoard.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springBoard.board.model.Board;
import com.springBoard.constant.ResponseStatus;
import com.springBoard.exception.AccessDeniedException;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.PostPermission;
import com.springBoard.token.model.Token;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSearchCond;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.util.TokenManager;
import com.springBoard.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

/**
 * @deprecated 
 */
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public LoginCheckInterceptor(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String boardUrl = request.getRequestURI().split("/")[2];
        Board board = Board.boardUrlMap.get("/" + boardUrl);
        if(board == null){
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.BOARD_URL_NOT_EXIST);
            throw new BadRequestException(apiResponse);
        }

        HttpSession session = request.getSession();
        String token = request.getHeader(Token.AT_HEADER);
        User tokenUser = null;
        if (token != null) {
            String subject = TokenManager.getSubject(token);
            tokenUser = objectMapper.readValue(
                    subject, new TypeReference<User>() {}
            );
        }
        // 정식 유저만 접근 가능
        if(board.getPermission().equals(PostPermission.USER)){
            if(token == null || tokenUser == null ){
                ApiResponse apiResponse = new ApiResponse(ResponseStatus.BOARD_ACCESS_DENIED);
                throw new AccessDeniedException(apiResponse);
            }
            if(tokenUser.getisUser() != 1){
                ApiResponse apiResponse = new ApiResponse(ResponseStatus.BOARD_ACCESS_DENIED);
                throw new AccessDeniedException(apiResponse);
            }
            return true;
        }
        // 비회원 전용
        else if(board.getPermission().equals(PostPermission.NOT_USER)){

        }
        // 모두 접근 가능
        // 비회원용 세션이 없음
        if(token == null || tokenUser == null){
            UserSearchCond userSearchCond = new UserSearchCond.Builder()
                    .isUser(0)
                    .hostIp(Util.getClientIp(request))
                    .build();
            Optional<User> user = userRepository.find(userSearchCond);

            // 비회원이지만 기존에 세션을 받은 이력이 있음.
//            if(user.isPresent()){
//                session.setAttribute("user",user.get());
//            }
//            // 비회원이고 기존에 세션을 받은 이력이 없음
//            else{
//                generateGuestUser(request);
//            }
        }
        // 비회원용 세션이 있음
        else{
            UserSearchCond userSearchCond = new UserSearchCond.Builder()
                    .hostIp(tokenUser.getHostIp())
                    .isUser(0)
                    .build();
            Optional<User> foundUser = userRepository.find(userSearchCond);

//            // db에 아직 살아있는 세션이라면
//            if(foundUser.isPresent()){
//                foundUser.get().setLastLogin(new Date());
//                userRepository.updateById(foundUser.get());
//                session.setAttribute("user",foundUser.get());
//            }
//            // 없어진 세션이라면 새로 만들어줌
//            else{
//                generateGuestUser(request);
//            }
        }
        request.setAttribute("tokenUser", tokenUser);
        return true;
    }

    public void generateGuestUser(HttpServletRequest request){
        HttpSession session = request.getSession();

        User newUser = new User.Builder()
                .isUser(0)
                .hostIp(Util.getClientIp(request))
                .userId(Util.generateRid())
                .userName(Util.generateRid())
                .password("unknown_user_pass")
                .build();

        userRepository.save(newUser);
        session.setAttribute("user",newUser);
    }
}

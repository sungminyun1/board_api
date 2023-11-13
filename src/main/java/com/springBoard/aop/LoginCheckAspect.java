package com.springBoard.aop;

import com.springBoard.board.model.Board;
import com.springBoard.constant.ResponseStatus;
import com.springBoard.exception.AccessDeniedException;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.PostPermission;
import com.springBoard.token.model.Token;
import com.springBoard.token.repository.TokenRepository;
import com.springBoard.user.model.User;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.user.service.UserService;
import com.springBoard.util.Util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

@Aspect
public class LoginCheckAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Around(value = "@annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            String url = request.getRequestURI().split("/")[2];
            Board board = Board.boardUrlMap.get("/" + url);
            if(board == null){
                ApiResponse apiResponse = new ApiResponse(ResponseStatus.BOARD_URL_NOT_EXIST);
                throw new BadRequestException(apiResponse);
            }
            String token = request.getHeader(Token.AT_HEADER);
            if(token == null){
                return checkGuest(joinPoint, board);
            }

            User loginUser = userService.getLoginUserByToken(token);
            if(loginUser == null){
                ApiResponse apiResponse = new ApiResponse(ResponseStatus.TOKEN_USER_NOT_FOUND);
                throw new BadRequestException(apiResponse);
            }

            if(board.getPermission().equals(PostPermission.USER)){
                if(token == null || loginUser == null ){
                    ApiResponse apiResponse = new ApiResponse(ResponseStatus.BOARD_ACCESS_DENIED);
                    throw new AccessDeniedException(apiResponse);
                }
                if(loginUser.getisUser() != 1){
                    ApiResponse apiResponse = new ApiResponse(ResponseStatus.BOARD_ACCESS_DENIED);
                    throw new AccessDeniedException(apiResponse);
                }
            }
            request.setAttribute("tokenUser", loginUser);

            return joinPoint.proceed();
        }catch (Exception e){
            log.error("loginCheck catch error {}", e.getClass() + e.getMessage());
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.LOGIN_CHECK_FAILED);
            throw new BadRequestException(apiResponse);
        }
    }

    private Object checkGuest(ProceedingJoinPoint joinPoint,Board board) throws Throwable{
        if (board.getPermission().equals(PostPermission.USER)) {
            ApiResponse apiResponse = new ApiResponse(ResponseStatus.BOARD_ACCESS_DENIED);
            throw new AccessDeniedException(apiResponse);
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        User newUser = new User.Builder()
                .isUser(0)
                .hostIp(Util.getClientIp(request))
                .userId(Util.generateRid())
                .userName(Util.generateRid())
                .password("unknown_user_pass")
                .build();

        userRepository.save(newUser);
        request.setAttribute("tokenUser", newUser);
        return joinPoint.proceed();
    }
}

package com.springBoard.interceptor;

import com.springBoard.board.model.Board;
import com.springBoard.exception.AccessDeniedException;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.PostPermission;
import com.springBoard.user.model.User;
import com.springBoard.user.model.UserSearchCond;
import com.springBoard.user.repository.UserRepository;
import com.springBoard.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

public class LoginCheckInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final UserRepository userRepository;

    public LoginCheckInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String boardUrl = request.getRequestURI().split("/")[2];
        Board board = Board.boardUrlMap.get("/" + boardUrl);
        if(board == null){
            ApiResponse apiResponse = new ApiResponse(false, "존재하지 않는 boardurl입니다. " + boardUrl);
            throw new BadRequestException(apiResponse);
        }

        HttpSession session = request.getSession();

        // 정식 유저만 접근 가능
        if(board.getPermission().equals(PostPermission.USER)){
            if(session == null || session.getAttribute("user") == null ){
                ApiResponse apiResponse = new ApiResponse(false, "허가되지 않은 접근입니다.");
                throw new AccessDeniedException(apiResponse);
            }
            User sessionUser = (User)session.getAttribute("user");
            if(sessionUser.getisUser() != 1){
                ApiResponse apiResponse = new ApiResponse(false, "허가되지 않은 접근입니다.");
                throw new AccessDeniedException(apiResponse);
            }
            return true;
        }
        // 비회원 전용
        else if(board.getPermission().equals(PostPermission.NOT_USER)){

        }
        // 모두 접근 가능
        // 비회원용 세션이 없음
        if(session == null || session.getAttribute("user") == null){
            UserSearchCond userSearchCond = new UserSearchCond.Builder()
                    .isUser(0)
                    .hostIp(Util.getClientIp(request))
                    .build();
            Optional<User> user = userRepository.find(userSearchCond);

            // 비회원이지만 기존에 세션을 받은 이력이 있음.
            if(user.isPresent()){
                session.setAttribute("user",user.get());
            }
            // 비회원이고 기존에 세션을 받은 이력이 없음
            else{
                generateGuestUser(request);
            }
        }
        // 비회원용 세션이 있음
        else{
            User sessionUser = (User)session.getAttribute("user");
            UserSearchCond userSearchCond = new UserSearchCond.Builder()
                    .hostIp(sessionUser.getHostIp())
                    .isUser(0)
                    .build();
            Optional<User> foundUser = userRepository.find(userSearchCond);

            // db에 아직 살아있는 세션이라면
            if(foundUser.isPresent()){
                foundUser.get().setLastLogin(new Date());
                userRepository.updateById(foundUser.get());
                session.setAttribute("user",foundUser.get());
            }
            // 없어진 세션이라면 새로 만들어줌
            else{
                generateGuestUser(request);
            }
        }
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

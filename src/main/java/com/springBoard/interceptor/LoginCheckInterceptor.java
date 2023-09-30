package com.springBoard.interceptor;

import com.springBoard.board.model.Board;
import com.springBoard.exception.AccessDeniedException;
import com.springBoard.exception.BadRequestException;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.PostPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String boardUrl = request.getRequestURI().split("/")[2];
        Board board = Board.boardUrlMap.get("/" + boardUrl);
        if(board == null){
            ApiResponse apiResponse = new ApiResponse(false, "존재하지 않는 boardurl입니다. " + boardUrl);
            throw new BadRequestException(apiResponse);
        }

        if(board.getPermission().equals(PostPermission.USER)){
            HttpSession session = request.getSession();

            if(session == null || session.getAttribute("user") == null){
                ApiResponse apiResponse = new ApiResponse(false, "허가되지 않은 접근입니다.");
                throw new AccessDeniedException(apiResponse);
            }
        }
        return true;
    }
}

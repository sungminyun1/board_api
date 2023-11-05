package com.springBoard.exception;

import com.springBoard.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private ApiResponse apiResponse;

    public AccessDeniedException(ApiResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiResponse getApiResponse(){
        return apiResponse;
    }
}

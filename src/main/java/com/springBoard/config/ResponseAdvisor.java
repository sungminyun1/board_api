package com.springBoard.config;

import com.springBoard.exception.ControllerExceptionHandler;
import com.springBoard.payload.ApiResponse;
import com.springBoard.payload.ApiResponseWithData;
import com.springBoard.post.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        if(returnType.getContainingClass() == ControllerExceptionHandler.class){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body == null){
            return new ApiResponse(true, "test");
        }
        return new ApiResponseWithData<>(true, "test", body);
    }
}

package com.springBoard.exception;

import com.springBoard.payload.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(DataIntegrityViolationException exception){
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(BadRequestException exception){
        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(AccessDeniedException exception){
        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        StringBuilder eMsg = new StringBuilder();
        for (FieldError filed : fieldErrors) {
            eMsg.append(filed.getField() + " - " + filed.getDefaultMessage() + " / ");
        }

        ApiResponse apiResponse = new ApiResponse(false, eMsg.toString());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}

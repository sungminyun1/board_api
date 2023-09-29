package com.springBoard.controller;

import com.springBoard.payload.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class PostController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping("/userOnly/post")
    public ResponseEntity<ApiResponse> getList(
            @RequestParam(required = false, defaultValue = "50") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset){

        return new ResponseEntity<>(new ApiResponse(), HttpStatus.OK);
    }
}

package com.springBoard.controller;

import com.springBoard.board.model.Board;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.PostWriteForm;
import com.springBoard.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/board")
public class PostController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{boardUrl}/post")
    public ResponseEntity<ApiResponse> getList(
            @PathVariable String boardUrl,
            @RequestParam(required = false, defaultValue = "50") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset){
        ApiResponse apiResponse = postService.getPostList(boardUrl, limit, offset);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/{boardUrl}/post")
    public ResponseEntity<ApiResponse> writePost(
            @PathVariable String boardUrl,
            @RequestBody @Validated PostWriteForm postWriteForm,
            HttpServletRequest request
            ){
        ApiResponse apiResponse = postService.writePost(boardUrl, postWriteForm, request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{boardUrl}/post/{postRid}")
    public ResponseEntity<ApiResponse> updatePost(
            @PathVariable String boardUrl,
            @PathVariable String postRid,
            @RequestBody @Validated PostWriteForm postWriteForm,
            HttpServletRequest request
    ){
        ApiResponse apiResponse = postService.updatePost(boardUrl, postRid, postWriteForm, request);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

package com.springBoard.controller;

import com.springBoard.aop.LoginCheck;
import com.springBoard.board.model.Board;
import com.springBoard.payload.ApiResponse;
import com.springBoard.post.model.Post;
import com.springBoard.post.model.PostWriteForm;
import com.springBoard.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/board")
public class PostController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @LoginCheck
    @GetMapping("/{boardUrl}/post")
    public List<Post> getList(
            @PathVariable String boardUrl,
            @RequestParam(required = false, defaultValue = "50") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset){
        return postService.getPostList(boardUrl, limit, offset);
    }

    @LoginCheck
    @PostMapping("/{boardUrl}/post")
    public Post writePost(
            @PathVariable String boardUrl,
            @RequestBody @Validated PostWriteForm postWriteForm,
            HttpServletRequest request
            ){
        return postService.writePost(boardUrl, postWriteForm, request);
    }

    @LoginCheck
    @PutMapping("/{boardUrl}/post/{postRid}")
    public Post updatePost(
            @PathVariable String boardUrl,
            @PathVariable String postRid,
            @RequestBody @Validated PostWriteForm postWriteForm,
            HttpServletRequest request
    ){
        return postService.updatePost(boardUrl, postRid, postWriteForm, request);
    }

    @LoginCheck
    @DeleteMapping("/{boardUrl}/post/{postRid}")
    public void deletePost(
            @PathVariable String boardUrl,
            @PathVariable String postRid,
            HttpServletRequest request
    ){
        postService.deletePost(boardUrl, postRid, request);
    }

    @LoginCheck
    @GetMapping("/{boardUrl}/post/{postRid}")
    public Post readPost(
            @PathVariable String boardUrl,
            @PathVariable String postRid,
            HttpServletRequest request
    ){
        return postService.readPost(boardUrl, postRid, request);
    }
}

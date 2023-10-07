package com.springBoard.controller;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.service.CommentService;
import com.springBoard.post.service.PostCommentCompositeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board/{boardUrl}/post/{postRid}/comment")
public class CommentController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final CommentService commentService;
    private final PostCommentCompositeService postCommentCompositeService;

    public CommentController(CommentService commentService, PostCommentCompositeService postCommentCompositeService) {
        this.commentService = commentService;
        this.postCommentCompositeService = postCommentCompositeService;
    }

    @GetMapping("")
    public List<Comment> getList(
            @PathVariable String boardUrl,
            @PathVariable String postRid,
            @RequestParam(required = false, defaultValue = "50") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset
    ){
        return postCommentCompositeService.getCommentList(postRid, limit, offset);
    }
}

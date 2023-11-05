package com.springBoard.controller;

import com.springBoard.comment.model.Comment;
import com.springBoard.comment.model.CommentWriteForm;
import com.springBoard.comment.service.CommentService;
import com.springBoard.post.service.PostCommentCompositeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("")
    public Comment writeComment(
            @PathVariable String boardUrl,
            @PathVariable String postRid,
            @RequestBody @Validated CommentWriteForm commentWriteForm,
            HttpServletRequest request
            ){

        return postCommentCompositeService.writeComment(postRid, commentWriteForm, request);
    }

    @PutMapping("/{commentRid}")
    public Comment updateComment(
            @PathVariable String commentRid,
            @RequestBody @Validated CommentWriteForm commentWriteForm,
            HttpServletRequest request
    ){
        return commentService.updateComment(commentRid, commentWriteForm, request);
    }

    @DeleteMapping("/{commentRid}")
    public void deleteComment(
            @PathVariable String commentRid,
            HttpServletRequest request
    ){
        commentService.deleteComment(commentRid,request);
    }
}

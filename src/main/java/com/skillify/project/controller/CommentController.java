package com.skillify.project.controller;

import com.skillify.project.model.Comment;
import com.skillify.project.service.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/forum/comments")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/answer/{answerId}")
    @Operation(summary = "Get Comments by Answer", description = "Fetches all comments related to the specified answer.")
    public ResponseEntity<List<Comment>> getCommentsByAnswer(@PathVariable String answerId) {
        return ResponseEntity.ok(commentService.getCommentsByAnswerId(answerId));
    }


    @PostMapping
    @Operation(summary = "Add Comment to Answer", description = "Allows a user to add a comment to a specific answer.")
    public Object addCommentToAnswer(@RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addCommentToAnswer(comment));
    }
}

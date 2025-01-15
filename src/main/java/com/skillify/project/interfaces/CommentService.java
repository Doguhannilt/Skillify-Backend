package com.skillify.project.interfaces;

import com.skillify.project.model.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByAnswerId(String answerId);
    ResponseEntity<String> addCommentToAnswer(Comment comment);
}


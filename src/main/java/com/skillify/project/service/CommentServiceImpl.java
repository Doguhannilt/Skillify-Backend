package com.skillify.project.service;

import com.skillify.project.interfaces.CommentService;
import com.skillify.project.model.Comment;
import com.skillify.project.repository.AnswerRepository;
import com.skillify.project.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;

    public CommentServiceImpl(CommentRepository commentRepository, AnswerRepository answerRepository) {
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Comment> getCommentsByAnswerId(String answerId) {
        return commentRepository.findByAnswerId(answerId);
    }

    @Override
    public ResponseEntity<String> addCommentToAnswer(Comment comment) {
        if (!answerRepository.existsById(comment.getAnswerId())) {
            throw new IllegalArgumentException("Answer does not exist.");
        }
        Comment savedComment = commentRepository.save(comment);
        if (savedComment == null || savedComment.getId() == null) {
            throw new IllegalStateException("Comment could not be saved.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Added " + savedComment.getId());

    }
}

package com.skillify.project.repository;

import com.skillify.project.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByAnswerId(String answerId);
}

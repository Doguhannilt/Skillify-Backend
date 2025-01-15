package com.skillify.project.repository;

import com.skillify.project.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// Answer Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {
    List<Answer> findByQuestionId(Long questionId);
}

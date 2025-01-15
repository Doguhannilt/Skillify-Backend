package com.skillify.project.repository;

import com.skillify.project.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// Question Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByForumTopicId(Long forumTopicId);
}

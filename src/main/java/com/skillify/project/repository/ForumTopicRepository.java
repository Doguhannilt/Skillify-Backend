package com.skillify.project.repository;

import com.skillify.project.model.ForumTopic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// ForumTopic Repository
public interface ForumTopicRepository extends MongoRepository<ForumTopic, String> {
    List<ForumTopic> findByInstructorId(Long instructorId);
}

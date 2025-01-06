package com.skillify.project.repository;

import com.skillify.project.model.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonRepository extends MongoRepository<Lesson, Long> {
}

package com.skillify.project.repository;

import com.skillify.project.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findByName(String name);
    List<Course> findByNameContainingIgnoreCase(String name);

    List<Course> findByInstructorId(String instructorId);
}

package com.skillify.project.repository;

import com.skillify.project.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnrollmentRepository extends MongoRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
}

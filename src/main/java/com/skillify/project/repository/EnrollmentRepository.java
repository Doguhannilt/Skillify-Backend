package com.skillify.project.repository;

import com.skillify.project.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollmentRepository extends MongoRepository<Enrollment, Long> {
}

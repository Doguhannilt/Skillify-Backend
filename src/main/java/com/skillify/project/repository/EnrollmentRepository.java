package com.skillify.project.repository;

import com.skillify.project.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {
    List<Enrollment> findByStudentId(String studentId);

    List<Enrollment> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);
}

package com.skillify.project.interfaces;

import com.skillify.project.model.Enrollment;
import org.springframework.http.ResponseEntity;

public interface EnrollmentService {
    ResponseEntity<String> createEnrollment(Long course_id, Long user_id) throws Exception;
    ResponseEntity<String> deleteEnrollment(Enrollment enrollment) throws Exception;
    boolean isCourseCompleted(Long enrollmentId) throws Exception;
}
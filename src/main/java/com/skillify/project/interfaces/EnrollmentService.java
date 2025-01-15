package com.skillify.project.interfaces;

import com.skillify.project.model.Enrollment;
import org.springframework.http.ResponseEntity;

public interface EnrollmentService {
    ResponseEntity<String> createEnrollment(String course_id, String user_id) throws Exception;
    ResponseEntity<String> deleteEnrollment(Enrollment enrollment) throws Exception;
    boolean isCourseCompleted(String enrollmentId) throws Exception;
}
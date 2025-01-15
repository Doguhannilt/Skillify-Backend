package com.skillify.project.controller;

import com.skillify.project.model.Enrollment;
import com.skillify.project.service.EnrollmentServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentServiceImp enrollmentService;
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);

    public EnrollmentController(EnrollmentServiceImp enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Operation(summary = "Create a new enrollment")
    @PostMapping("/create")
    public ResponseEntity<String> createEnrollment(@RequestParam String courseId, @RequestParam String userId) {
        logger.info("Creating enrollment for courseId: {} and userId: {}", courseId, userId);
        try {
            return enrollmentService.createEnrollment(courseId, userId);
        } catch (Exception e) {
            logger.error("Error while creating enrollment: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete an enrollment")
    @DeleteMapping("/delete/{enrollmentId}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable String enrollmentId) {
        logger.info("Deleting enrollment with ID: {}", enrollmentId);
        try {
            Enrollment enrollment = new Enrollment();
            enrollment.setId(enrollmentId);
            return enrollmentService.deleteEnrollment(enrollment);
        } catch (Exception e) {
            logger.error("Error while deleting enrollment: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Check if a course is completed")
    @GetMapping("/is-completed/{enrollmentId}")
    public ResponseEntity<String> isCourseCompleted(@PathVariable String enrollmentId) {
        logger.info("Checking if course is completed for enrollment ID: {}", enrollmentId);
        try {
            boolean isCompleted = enrollmentService.isCourseCompleted(enrollmentId);
            if (isCompleted) {
                logger.info("Course with enrollment ID: {} is completed", enrollmentId);
                return ResponseEntity.ok("The course is completed.");
            } else {
                logger.info("Course with enrollment ID: {} is not completed yet", enrollmentId);
                return ResponseEntity.ok("The course is not completed yet.");
            }
        } catch (Exception e) {
            logger.error("Error while checking course completion: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

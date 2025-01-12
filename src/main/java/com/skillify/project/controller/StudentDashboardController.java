package com.skillify.project.controller;

import com.skillify.project.model.Course;
import com.skillify.project.service.StudentDashboardServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/dashboard")
public class StudentDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(StudentDashboardController.class);
    private final StudentDashboardServiceImp dashboardService;

    public StudentDashboardController(StudentDashboardServiceImp dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Get enrolled courses by studentId
    @Operation(summary = "Get Enrolled Courses by studentId", description = "Retrieve the list of courses the student is enrolled in")
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getEnrolledCourses(@RequestParam Long studentId) {
        try {
            logger.info("Fetching enrolled courses for studentId: {}", studentId);
            List<Course> enrolledCourses = dashboardService.getEnrolledCourses(studentId);
            logger.info("Fetched {} courses for studentId: {}", enrolledCourses.size(), studentId);
            return ResponseEntity.ok(enrolledCourses);
        } catch (Exception e) {
            logger.error("Error fetching courses for studentId: {}", studentId, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    // Get lesson progress by lessonProgressId
    @Operation(summary = "Get completed lesson", description = "Mark a lesson as completed by its lessonProgressId")
    @PostMapping("/lessons/{lessonProgressId}/complete")
    public ResponseEntity<Void> markLessonAsCompleted(@PathVariable Long lessonProgressId) {
        try {
            logger.info("Marking lesson with progressId: {} as completed", lessonProgressId);
            dashboardService.markLessonAsCompleted(lessonProgressId);
            logger.info("Lesson with progressId: {} marked as completed", lessonProgressId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error marking lesson with progressId: {} as completed", lessonProgressId, e);
            return ResponseEntity.status(500).build();
        }
    }
}

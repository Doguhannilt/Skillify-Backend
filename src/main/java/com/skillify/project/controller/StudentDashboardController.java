package com.skillify.project.controller;

import com.skillify.project.model.Course;
import com.skillify.project.service.StudentDashboardServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/dashboard")
public class StudentDashboardController {

    private final StudentDashboardServiceImp dashboardService;

    public StudentDashboardController(StudentDashboardServiceImp dashboardService) {
        this.dashboardService = dashboardService;
    }

	// Get enrolled courses by
    @Operation(summary = "Get Enrolled Courses by studentId")
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getEnrolledCourses(@RequestParam Long studentId) {
        return ResponseEntity.ok(dashboardService.getEnrolledCourses(studentId));
    }

	// Get lesson progress by lessonProgressId
    @Operation(summary = "Get completed lesson ")
    @PostMapping("/lessons/{lessonProgressId}/complete")
    public ResponseEntity<Void> markLessonAsCompleted(@PathVariable Long lessonProgressId) {
        dashboardService.markLessonAsCompleted(lessonProgressId);
        return ResponseEntity.ok().build();
    }
}

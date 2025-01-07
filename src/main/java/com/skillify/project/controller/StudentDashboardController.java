package com.skillify.project.controller;

import com.skillify.project.model.Enrollment;
import com.skillify.project.service.StudentDashboardImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/dashboard")
public class StudentDashboardController {

    private final StudentDashboardImp dashboardService;

    public StudentDashboardController(StudentDashboardImp dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Enrollment>> getEnrolledCourses(@RequestParam Long studentId) {
        return ResponseEntity.ok(dashboardService.getEnrolledCourses(studentId));
    }

    @PostMapping("/lessons/{lessonProgressId}/complete")
    public ResponseEntity<Void> markLessonAsCompleted(@PathVariable Long lessonProgressId) {
        dashboardService.markLessonAsCompleted(lessonProgressId);
        return ResponseEntity.ok().build();
    }
}

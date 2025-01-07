package com.skillify.project.controller;

import com.skillify.project.model.Course;
import com.skillify.project.service.InstructorDashboardServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/instructor/dashboard")
public class InstructorDashboardController {

    private final InstructorDashboardServiceImp instructorDashboardServiceImp;

    public InstructorDashboardController(InstructorDashboardServiceImp dashboardService) {
        this.instructorDashboardServiceImp = dashboardService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getInstructorCourses(@RequestParam Long instructorId) throws Exception {
        return ResponseEntity.ok(instructorDashboardServiceImp.getInstructorCourses(instructorId));
    }
}
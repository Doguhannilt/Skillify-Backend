package com.skillify.project.controller;

import com.skillify.project.model.Course;
import com.skillify.project.service.InstructorDashboardServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/instructor/dashboard")
public class InstructorDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(InstructorDashboardController.class);
    private final InstructorDashboardServiceImp instructorDashboardServiceImp;

    public InstructorDashboardController(InstructorDashboardServiceImp instructorDashboardServiceImp) {
        this.instructorDashboardServiceImp = instructorDashboardServiceImp;
    }

    // Getting courses by instructorId
    @Operation(summary = "Get Courses", description = "Get Courses By Instructor Id")
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getInstructorCourses(@RequestParam Long instructorId) throws Exception {
        try {
            logger.info("Fetching courses for instructor with ID: {}", instructorId);
            List<Course> courses = instructorDashboardServiceImp.getInstructorCourses(instructorId);
            if (courses.isEmpty()) {
                logger.warn("No courses found for instructor with ID: {}", instructorId);
                return ResponseEntity.noContent().build();
            }
            logger.info("Successfully fetched {} courses for instructor with ID: {}", courses.size(), instructorId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            logger.error("Error fetching courses for instructor with ID: {}", instructorId, e);
            return ResponseEntity.status(500).body(null);  // Internal Server Error
        }
    }
}

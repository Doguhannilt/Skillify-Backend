package com.skillify.project.controller;

import com.skillify.project.model.Course; // Course modelini temsil eden sınıf
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.service.CourseRecommendationService;
import com.skillify.project.service.SearchServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchServiceImp searchService;
    private final CourseRepository courseRepository;
    private final CourseRecommendationService recommendationService;

    public SearchController(SearchServiceImp searchService, CourseRepository courseRepository, CourseRecommendationService courseRecommendationService) {
        this.searchService = searchService;
        this.courseRepository = courseRepository;
        this.recommendationService = courseRecommendationService;
    }

    @Operation(summary = "Get all courses")
    @GetMapping("/courses")
    @Cacheable(value = "allCourses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return searchService.getAllCourses();
    }

    @Operation(summary = "Get courses by name")
    @GetMapping("/courses/name")
    @Cacheable(value = "coursesByName", key = "#name")
    public ResponseEntity<List<Course>> filterByName(@RequestParam String name) {
        return searchService.filterByName(name);
    }

    @Operation(summary = "Get courses by Instructor name")
    @GetMapping("/courses/instructor")
    @Cacheable(value = "coursesByInstructor", key = "#instructorName")
    public ResponseEntity<List<Course>> filterByInstructorName(@RequestParam String instructorName) {
        return searchService.filterByInstructorName(instructorName);
    }

    @Operation(summary = "Course Recommendation Service - TF-IDF")
    @GetMapping("/{courseId}/recommendations")
    @Cacheable(value = "courseRecommendations", key = "#courseId")
    public List<Course> getRecommendedCourses(@PathVariable Long courseId) {
        Course targetCourse = courseRepository.findById(String.valueOf(courseId))
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Course> allCourses = courseRepository.findAll();
        return recommendationService.recommendCourses(targetCourse, allCourses);
    }
}

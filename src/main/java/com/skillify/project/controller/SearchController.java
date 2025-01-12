package com.skillify.project.controller;

import com.skillify.project.model.Course; // Course modelini temsil eden sınıf
import com.skillify.project.service.SearchServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchServiceImp searchService;

    public SearchController(SearchServiceImp searchService) {
        this.searchService = searchService;
    }

    @Operation(summary = "Get all courses")
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return searchService.getAllCourses();
    }

    @Operation(summary = "Get courses by name")
    @GetMapping("/courses/name")
    public ResponseEntity<List<Course>> filterByName(@RequestParam String name) {
        return searchService.filterByName(name);
    }

    @Operation(summary = "Get courses by Instructor name")
    @GetMapping("/courses/instructor")
    public ResponseEntity<List<Course>> filterByInstructorName(@RequestParam String instructorName) {
        return searchService.filterByInstructorName(instructorName);
    }
}

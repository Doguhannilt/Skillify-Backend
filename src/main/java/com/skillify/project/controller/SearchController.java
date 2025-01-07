package com.skillify.project.controller;

import com.skillify.project.model.Course; // Course modelini temsil eden sınıf
import com.skillify.project.service.SearchServiceImp;
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

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return searchService.getAllCourses();
    }

    @GetMapping("/courses/name")
    public ResponseEntity<List<Course>> filterByName(@RequestParam String name) {
        return searchService.filterByName(name);
    }

    @GetMapping("/courses/instructor")
    public ResponseEntity<List<Course>> filterByInstructorName(@RequestParam String instructorName) {
        return searchService.filterByInstructorName(instructorName);
    }
}

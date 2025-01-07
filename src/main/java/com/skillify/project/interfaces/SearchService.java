package com.skillify.project.interfaces;

import com.skillify.project.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SearchService {
    ResponseEntity<List<Course>> getAllCourses() throws Exception;
    ResponseEntity<List<Course>> filterByName(@RequestParam String name) throws Exception;
    ResponseEntity<List<Course>> filterByInstructorName(@RequestParam String instructorName) throws Exception;
}

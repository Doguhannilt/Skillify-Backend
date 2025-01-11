package com.skillify.project.service;

import com.skillify.project.interfaces.SearchService;
import com.skillify.project.model.Course;
import com.skillify.project.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImp implements SearchService {

    private final CourseRepository courseRepository;

    public SearchServiceImp(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }

    @Override
    public ResponseEntity<List<Course>> filterByName(String name) {
        List<Course> courses = courseRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(courses);
    }

    @Override
    public ResponseEntity<List<Course>> filterByInstructorName(String instructorName) {
        List<Course> courses = courseRepository.findByNameContainingIgnoreCase(instructorName);
        return ResponseEntity.ok(courses);
    }
}

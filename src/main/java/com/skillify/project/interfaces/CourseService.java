package com.skillify.project.interfaces;

import com.skillify.project.model.Course;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    Course createCourse(Course course) throws Exception;
    ResponseEntity<String> deleteCourse(Course course) throws Exception;
    ResponseEntity<String> updateCourse(Course course) throws Exception;
}

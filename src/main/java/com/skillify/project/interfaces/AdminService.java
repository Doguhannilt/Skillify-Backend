package com.skillify.project.interfaces;

import com.skillify.project.model.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    ResponseEntity<List<User>> getAllUsers() throws Exception;
    ResponseEntity<Optional<User>> getUserById(User user) throws Exception;
    void deleteUserById(User user) throws Exception;
    ResponseEntity<List<Course>> getAllCourses(Course course) throws Exception;
    ResponseEntity<Course> getCourseById(Course course) throws Exception;
    ResponseEntity<List<Lesson>> getAllLessons(Lesson lesson) throws Exception;
    ResponseEntity<Lesson> getLessonById(Lesson lesson) throws Exception;
    void deleteLessonById(Lesson lesson) throws Exception;
    ResponseEntity<List<Enrollment>> getAllEnrollments() throws Exception;
    ResponseEntity<List<Review>> getAllReviews() throws Exception;
    ResponseEntity<List<Course>> getPopularCourses() throws Exception;
    ResponseEntity<List<User>> getActiveUsers() throws Exception;
    ResponseEntity<List<Enrollment>> getEnrollmentsWithinRange(LocalDate startDate, LocalDate endDate) throws Exception;
}

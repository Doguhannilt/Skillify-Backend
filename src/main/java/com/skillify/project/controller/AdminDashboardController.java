package com.skillify.project.controller;


import com.skillify.project.model.*;
import com.skillify.project.service.AdminServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminServiceImp adminServiceImp;

    public AdminDashboardController(AdminServiceImp adminServiceImp) {
        this.adminServiceImp = adminServiceImp;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        return adminServiceImp.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) throws Exception {
        User user = new User();
        user.setId(id);
        return adminServiceImp.getUserById(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) throws Exception {
        User user = new User();
        user.setId(id);
        adminServiceImp.deleteUserById(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() throws Exception {
        return adminServiceImp.getAllCourses(null);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) throws Exception {
        Course course = new Course();
        course.setId(id);
        return adminServiceImp.getCourseById(course);
    }

    @GetMapping("/lessons")
    public ResponseEntity<List<Lesson>> getAllLessons() throws Exception {
        return adminServiceImp.getAllLessons(null);
    }

    @GetMapping("/lessons/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        return adminServiceImp.getLessonById(lesson);
    }

    @DeleteMapping("/lessons/{id}")
    public ResponseEntity<Void> deleteLessonById(@PathVariable Long id) throws Exception {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        adminServiceImp.deleteLessonById(lesson);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() throws Exception {
        return adminServiceImp.getAllEnrollments();
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() throws Exception{
        return adminServiceImp.getAllReviews();
    }
}

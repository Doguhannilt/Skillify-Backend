package com.skillify.project.controller;

import com.skillify.project.model.*;
import com.skillify.project.service.AdminServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardController.class);

    private final AdminServiceImp adminServiceImp;

    public AdminDashboardController(AdminServiceImp adminServiceImp) {
        this.adminServiceImp = adminServiceImp;
    }

    // Getting all users
    @Operation(summary = "Get all users")
    @GetMapping("/users")
    @Cacheable(value = "users")
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        logger.info("Fetching all users");
        return adminServiceImp.getAllUsers();
    }

    // Getting users by id
    @Operation(summary = "Get user by id")
    @GetMapping("/users/{id}")
    @Cacheable(value = "user", key = "#id")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) throws Exception {
        logger.info("Fetching user by ID: {}", id);
        User user = new User();
        user.setId(id);
        return adminServiceImp.getUserById(user);
    }

    // Deleting user by id
    @Operation(summary = "Delete user by id")
    @DeleteMapping("/users/{id}")
    @CacheEvict(value = "user", key = "#id")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) throws Exception {
        logger.info("Deleting user with ID: {}", id);
        User user = new User();
        user.setId(id);
        adminServiceImp.deleteUserById(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Getting all courses
    @Operation(summary = "Get all courses")
    @GetMapping("/courses")
    @Cacheable(value = "courses")
    public ResponseEntity<List<Course>> getAllCourses() throws Exception {
        logger.info("Fetching all courses");
        return adminServiceImp.getAllCourses(null);
    }

    // Getting course by id
    @Operation(summary = "Get course by id")
    @GetMapping("/courses/{id}")
    @Cacheable(value = "course", key = "#id")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) throws Exception {
        logger.info("Fetching course by ID: {}", id);
        Course course = new Course();
        course.setId(id);
        return adminServiceImp.getCourseById(course);
    }

    // Getting all lessons
    @Operation(summary = "Get all lessons")
    @GetMapping("/lessons")
    @Cacheable(value = "lessons")
    public ResponseEntity<List<Lesson>> getAllLessons() throws Exception {
        logger.info("Fetching all lessons");
        return adminServiceImp.getAllLessons(null);
    }

    // Getting lesson by id
    @Operation(summary = "Get lesson by id")
    @GetMapping("/lessons/{id}")
    @Cacheable(value = "lesson", key = "#id")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) throws Exception {
        logger.info("Fetching lesson by ID: {}", id);
        Lesson lesson = new Lesson();
        lesson.setId(id);
        return adminServiceImp.getLessonById(lesson);
    }

    // Deleting lesson by id
    @Operation(summary = "Delete lesson by id")
    @DeleteMapping("/lessons/{id}")
    @CacheEvict(value = "lesson", key = "#id")
    public ResponseEntity<Void> deleteLessonById(@PathVariable Long id) throws Exception {
        logger.info("Deleting lesson with ID: {}", id);
        Lesson lesson = new Lesson();
        lesson.setId(id);
        adminServiceImp.deleteLessonById(lesson);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Getting all enrollments
    @Operation(summary = "Get all enrollments")
    @GetMapping("/enrollments")
    @Cacheable(value = "enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() throws Exception {
        logger.info("Fetching all enrollments");
        return adminServiceImp.getAllEnrollments();
    }

    // Getting all reviews
    @Operation(summary = "Get all reviews")
    @GetMapping("/reviews")
    @Cacheable(value = "reviews")
    public ResponseEntity<List<Review>> getAllReviews() throws Exception {
        logger.info("Fetching all reviews");
        return adminServiceImp.getAllReviews();
    }

    // Getting popular courses
    @Operation(summary = "Get popular courses")
    @GetMapping("/popular-courses")
    @Cacheable(value = "popularCourses")
    public ResponseEntity<List<Course>> getPopularCourses() throws Exception {
        logger.info("Fetching popular courses");
        List<Course> popularCourses = adminServiceImp.getPopularCourses().getBody();
        return ResponseEntity.status(HttpStatus.OK).body(popularCourses);
    }

    // Getting active-users (threshold 10)
    @Operation(summary = "Get active users", description = "Threshold: 10 days")
    @GetMapping("/active-users")
    @Cacheable(value = "activeUsers")
    public ResponseEntity<List<User>> getActiveUsers() throws Exception {
        logger.info("Fetching active users with threshold of 10 days");
        List<User> activeUsers = adminServiceImp.getActiveUsers().getBody();
        return ResponseEntity.status(HttpStatus.OK).body(activeUsers);
    }

    // Getting enrollments (StartDate - EndDate)
    @Operation(summary = "Get enrollments", description = "StartDate and EndDate required")
    @GetMapping("/enrollments/range")
    @Cacheable(value = "enrollmentsRange", key = "#startDate + #endDate")
    public ResponseEntity<List<Enrollment>> getEnrollmentsWithinRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) throws Exception {

        logger.info("Fetching enrollments between {} and {}", startDate, endDate);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Enrollment> enrollments = adminServiceImp.getEnrollmentsWithinRange(start, end).getBody();
        return ResponseEntity.status(HttpStatus.OK).body(enrollments);
    }
}

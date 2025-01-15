package com.skillify.project.controller;

import com.skillify.project.model.Course;
import com.skillify.project.model.Enrollment;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.EnrollmentRepository;
import com.skillify.project.service.CourseServiceImp;
import com.skillify.project.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseServiceImp courseServiceImp;
    private final PaymentService paymentService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    public CourseController(CourseServiceImp courseServiceImp, PaymentService paymentService, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository)
    {
        this.courseServiceImp = courseServiceImp;
        this.paymentService = paymentService;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }



    // Create a course
    @Operation(summary = "Create a new course")
    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        try {
            logger.info("Attempting to create a course with name: {}", course.getName());
            // Course creation
            Course createdCourse = courseServiceImp.createCourse(course);

            // Null control for price
            if (createdCourse == null) {
                logger.error("Course creation failed for course: {}", course.getName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course creation failed");
            }
            Integer price = createdCourse.getPrice();
            if (price == null) {
                logger.error("Price is null for course: {}", course.getName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price cannot be null");
            }

            // Check if price is valid
            if (price < 0 || price > 10000) {
                logger.error("Invalid price for course {}: {}", course.getName(), price);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price should be valid");
            }

            // Description length control
            if (createdCourse.getDescription().length() < 10) {
                logger.error("Description is too short for course: {}", course.getName());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description length must be longer than 10 characters");
            }

            logger.info("Course successfully created with ID: {}", createdCourse.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Course is created");
        } catch (Exception e) {
            logger.error("Error during course creation: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Delete a course
    @Operation(summary = "Delete a course")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestBody Course course) {
        try {
            logger.info("Attempting to delete course with ID: {}", course.getId());
            ResponseEntity<String> deletedCourse = courseServiceImp.deleteCourse(course);
            if (deletedCourse == null) {
                logger.error("Course deletion failed for course ID: {}", course.getId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course deletion failed");
            }

            logger.info("Course successfully deleted with ID: {}", course.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course has been deleted");
        } catch (Exception e) {
            logger.error("Error during course deletion: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update a course
    @Operation(summary = "Update course")
    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        try {
            logger.info("Attempting to update course with ID: {}", course.getId());
            ResponseEntity<String> updatedCourse = courseServiceImp.updateCourse(course);
            if (updatedCourse == null) {
                logger.error("Course update failed for course ID: {}", course.getId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course update failed");
            }
            logger.info("Course successfully updated with ID: {}", course.getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course has been updated: " + updatedCourse.getBody());
        } catch (Exception e) {
            logger.error("Error during course update: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Purchase a course")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/{courseId}/purchase")
    public ResponseEntity<String> purchaseCourse(@PathVariable String courseId, @RequestParam String userId) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(String.valueOf(courseId));
            if (courseOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Course not found");
            }

            Course course = courseOptional.get();
            if (course.isPurchased()) {
                return ResponseEntity.badRequest().body("Course already purchased");
            }

            // Ödeme işlemi
            String paymentSuccessful = paymentService.createCheckoutSession(course.getName(), course.getPrice());
            if (!paymentSuccessful.isEmpty()) {
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Payment failed");
            }

            // Kursu kullanıcıya açma
            course.setPurchased(true);
            courseRepository.save(course);

            // Kullanıcıyı kayıt etmek
            Enrollment enrollment = new Enrollment();
            enrollment.setCourseId(courseId);
            enrollment.setStudentId(userId);
            enrollmentRepository.save(enrollment);

            return ResponseEntity.ok("Course purchased successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }
}
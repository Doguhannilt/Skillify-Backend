package com.skillify.project.controller;

import com.skillify.project.model.Review;
import com.skillify.project.service.ReviewServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewServiceImp reviewServiceImp;

    public ReviewController(ReviewServiceImp reviewServiceImp) {
        this.reviewServiceImp = reviewServiceImp;
    }

    // Create a review
    @Operation(summary = "Create a review", description = "Create a new review for a course")
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@ModelAttribute Review review) {
        try {
            logger.info("Creating review for course ID: {}", review.getCourse().getId());
            ResponseEntity<String> response = reviewServiceImp.createReview(review);
            logger.info("Review created successfully for course ID: {}", review.getCourse().getId());
            return response;
        } catch (Exception e) {
            logger.error("Error creating review for course ID: {}", review.getCourse().getId(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Delete a review by id
    @Operation(summary = "Delete a review by id", description = "Delete a review by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        try {
            logger.info("Deleting review with ID: {}", id);
            ResponseEntity<String> response = reviewServiceImp.deleteReview(id);
            logger.info("Review with ID: {} deleted successfully", id);
            return response;
        } catch (Exception e) {
            logger.error("Error deleting review with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

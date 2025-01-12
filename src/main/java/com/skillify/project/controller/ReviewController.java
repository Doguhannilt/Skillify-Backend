package com.skillify.project.controller;

import com.skillify.project.interfaces.ReviewService;
import com.skillify.project.model.Review;
import com.skillify.project.service.ReviewServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewServiceImp reviewServiceImp;

    public ReviewController(ReviewServiceImp reviewServiceImp) {
        this.reviewServiceImp = reviewServiceImp;
    }

	// Create a review
    @Operation(summary = "Create a review")
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@ModelAttribute Review review) {
        try {
            return reviewServiceImp.createReview(review);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	// Delete a review by
    @Operation(summary = "Delete a review by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        try {
            return reviewServiceImp.deleteReview(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


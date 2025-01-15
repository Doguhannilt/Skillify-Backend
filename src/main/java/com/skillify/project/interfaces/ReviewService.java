package com.skillify.project.interfaces;

import com.skillify.project.model.Review;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<String> createReview(Review review) throws Exception;
    ResponseEntity<String> deleteReview(String id) throws Exception;
}

package com.skillify.project.service;

import com.skillify.project.interfaces.ReviewService;
import com.skillify.project.model.Course;
import com.skillify.project.model.Review;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.ReviewRepository;
import com.skillify.project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImp implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public ReviewServiceImp(ReviewRepository reviewRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> createReview(Review review) throws Exception {

        Optional<User> isStudentValid = userRepository.findById(Long.valueOf(review.getStudent().getId()));
        if (isStudentValid.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        Optional<Course> isCourseValid = courseRepository.findById(review.getCourse().getId());
        if (isCourseValid.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Review savedReview = reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body("Review created: " + savedReview);
    }

    @Override
    public ResponseEntity<String> deleteReview(Long id) throws Exception {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
        }
        reviewRepository.deleteById(id); // Sadece ID'yi geçiyoruz.
        return ResponseEntity.status(HttpStatus.OK).body("Review deleted successfully");
    }
}

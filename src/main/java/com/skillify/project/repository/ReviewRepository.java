package com.skillify.project.repository;

import com.skillify.project.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, Long> {
}

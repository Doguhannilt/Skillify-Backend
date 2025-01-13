package com.skillify.project.repository;

import com.skillify.project.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, Long> {
}

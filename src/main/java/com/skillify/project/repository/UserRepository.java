package com.skillify.project.repository;

import com.skillify.project.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByEmail(String Email);
    Boolean existsByEmail(String Email);
    Optional<User> findById(ObjectId instructorObjectId);
    List<User> findByLastLoginAfter(LocalDate thirtyDaysAgo);
}

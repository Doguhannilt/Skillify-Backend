package com.skillify.project.repository;

import com.skillify.project.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String Email);
    Boolean existsByEmail(String Email);
    List<User> findByLastLoginAfter(LocalDate thirtyDaysAgo);
    Optional<User> findByName(String name);
}

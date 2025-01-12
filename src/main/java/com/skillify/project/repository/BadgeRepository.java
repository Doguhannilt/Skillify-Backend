package com.skillify.project.repository;

import com.skillify.project.model.Badge;
import com.skillify.project.model.Course;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BadgeRepository extends MongoRepository<Badge, String> {
    List<Badge> findAllById(List<Long> earnedBadges);
}

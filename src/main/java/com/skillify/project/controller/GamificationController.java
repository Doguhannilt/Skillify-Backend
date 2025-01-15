package com.skillify.project.controller;

import com.skillify.project.model.Badge;
import com.skillify.project.model.User;
import com.skillify.project.repository.BadgeRepository;
import com.skillify.project.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gamification")
@Tag(name = "Gamification", description = "Endpoints for gamification features")
public class GamificationController {
    private static final Logger logger = LoggerFactory.getLogger(GamificationController.class);

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    public GamificationController(UserRepository userRepository, BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }

    @Operation(summary = "Get User Badges", description = "Fetch the list of badges earned by a specific user")
    @GetMapping("/user/{userId}/badges")
    public ResponseEntity<List<Badge>> getUserBadges(@PathVariable String userId) {
        logger.info("Fetching badges for user with ID: {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            logger.warn("User with ID {} not found", userId);
            return ResponseEntity.badRequest().body(null);
        }

        User user = userOptional.get();
        List<Badge> earnedBadges = badgeRepository.findAllById(user.getEarnedBadges());
        logger.info("Badges fetched successfully for user with ID: {}", userId);
        return ResponseEntity.ok(earnedBadges);
    }
}

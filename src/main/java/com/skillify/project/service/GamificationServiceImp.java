package com.skillify.project.service;

import com.skillify.project.interfaces.GamificationService;
import com.skillify.project.model.GamificationEvents;
import com.skillify.project.model.User;
import com.skillify.project.model.Badge;
import com.skillify.project.repository.BadgeRepository;
import com.skillify.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GamificationServiceImp implements GamificationService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    public GamificationServiceImp(UserRepository userRepository, BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }

    public void addPointsToUser(Long userId, GamificationEvents event) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        user.setTotalPoints(user.getTotalPoints() + event.getPoints());

        List<Badge> availableBadges = badgeRepository.findAll();
        for (Badge badge : availableBadges) {
            if (user.getTotalPoints() >= badge.getPointsRequired() &&
                    (user.getEarnedBadges() == null || !user.getEarnedBadges().contains(badge.getId()))) {
                user.getEarnedBadges().add(badge.getId());
            }
        }

        userRepository.save(user);
    }
}

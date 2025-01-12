package com.skillify.project.interfaces;

import com.skillify.project.model.GamificationEvents;

public interface GamificationService {
    void addPointsToUser(Long userId, GamificationEvents event);
}

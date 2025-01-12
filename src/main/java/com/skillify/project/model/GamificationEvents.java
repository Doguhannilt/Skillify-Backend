package com.skillify.project.model;

public enum GamificationEvents {
    COURSE_COMPLETION(100),
    DAILY_LESSON_COMPLETION(10),
    FULL_COURSE_COMPLETION(200);

    private final int points;

    GamificationEvents(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}

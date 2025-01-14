package com.skillify.project.service;

import com.skillify.project.model.Course;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseRecommendationService {

    private final CosineSimilarityService cosineSimilarityService;

    public CourseRecommendationService(CosineSimilarityService cosineSimilarityService) {
        this.cosineSimilarityService = cosineSimilarityService;
    }

    public List<Course> recommendCourses(Course targetCourse, List<Course> allCourses) {
        Map<Course, Double> similarityScores = new HashMap<>();

        for (Course course : allCourses) {
            if (!course.getId().equals(targetCourse.getId())) {
                double similarity = cosineSimilarityService.calculateSimilarity(
                        targetCourse.getDescription(),
                        course.getDescription()
                );
                similarityScores.put(course, similarity);
            }
        }

        // Benzerlik skorlarına göre sıralama
        return similarityScores.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5) // İlk 5 kurs
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

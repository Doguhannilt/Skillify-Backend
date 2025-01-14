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
            double similarity = cosineSimilarityService.calculateSimilarity(course.getDescription(), targetCourse.getDescription());
            similarityScores.put(course, similarity);
        }

        return similarityScores.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }



}

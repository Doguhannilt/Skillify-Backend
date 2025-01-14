package com.skillify.project.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.skillify.project.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CourseRecommendationServiceTest {

    @Mock
    private CosineSimilarityService cosineSimilarityService;

    private CourseRecommendationService courseRecommendationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        courseRecommendationService = new CourseRecommendationService(cosineSimilarityService);
    }


    @Test
    public void testRecommendCourses() {
        // Test verilerini oluştur
        Course course1 = new Course(1L, "Course 1", "Machine learning basics", 49);
        Course course2 = new Course(2L, "Course 2", "Deep learning fundamentals", 59);
        Course course3 = new Course(3L, "Course 3", "Introduction to AI", 39);
        Course course4 = new Course(4L, "Course 4", "Data science for beginners", 45);
        Course course5 = new Course(5L, "Course 5", "Advanced machine learning", 69);

        Course targetCourse = new Course(6L, "Target Course", "Intro to Machine Learning", 59);

        List<Course> allCourses = Arrays.asList(course1, course2, course3, course4, course5);

        // Mocking benzerlik hesaplamalarını
        when(cosineSimilarityService.calculateSimilarity(course1.getDescription(), targetCourse.getDescription()))
                .thenReturn(0.8);
        when(cosineSimilarityService.calculateSimilarity(course2.getDescription(), targetCourse.getDescription()))
                .thenReturn(0.9);
        when(cosineSimilarityService.calculateSimilarity(course3.getDescription(), targetCourse.getDescription()))
                .thenReturn(0.7);
        when(cosineSimilarityService.calculateSimilarity(course4.getDescription(), targetCourse.getDescription()))
                .thenReturn(0.6);
        when(cosineSimilarityService.calculateSimilarity(course5.getDescription(), targetCourse.getDescription()))
                .thenReturn(0.95);

        // Test çıktıları (console'da yazdırma)
        System.out.println("Course 1 vs Target: " + cosineSimilarityService.calculateSimilarity(course1.getDescription(), targetCourse.getDescription()));
        System.out.println("Course 2 vs Target: " + cosineSimilarityService.calculateSimilarity(course2.getDescription(), targetCourse.getDescription()));
        System.out.println("Course 3 vs Target: " + cosineSimilarityService.calculateSimilarity(course3.getDescription(), targetCourse.getDescription()));
        System.out.println("Course 4 vs Target: " + cosineSimilarityService.calculateSimilarity(course4.getDescription(), targetCourse.getDescription()));
        System.out.println("Course 5 vs Target: " + cosineSimilarityService.calculateSimilarity(course5.getDescription(), targetCourse.getDescription()));

        // Metodu çağır ve sonucu kontrol et
        List<Course> recommendedCourses = courseRecommendationService.recommendCourses(targetCourse, allCourses);

        // Konsolda kaç kurs döndüğünü yazdır
        System.out.println("RecommendedCourse Size" +recommendedCourses.size());

        // Önerilen kursların sırasının doğru olduğunu doğrula
        assertEquals(5, recommendedCourses.size(), "Toplam önerilen kurs sayısı 5 olmalı");

        // İçerik karşılaştırması
        assertEquals(course5.getId(), recommendedCourses.get(0).getId());
        assertEquals(course5.getName(), recommendedCourses.get(0).getName());
        assertEquals(course5.getDescription(), recommendedCourses.get(0).getDescription());
        assertEquals(course5.getPrice(), recommendedCourses.get(0).getPrice());
        assertEquals(course5.getLanguage(), recommendedCourses.get(0).getLanguage());
        assertEquals(course5.getStatus(), recommendedCourses.get(0).getStatus());
        assertEquals(course5.getInstructorId(), recommendedCourses.get(0).getInstructorId());
        assertEquals(course5.isPurchased(), recommendedCourses.get(0).isPurchased());
    }

}
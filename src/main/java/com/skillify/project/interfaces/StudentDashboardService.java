package com.skillify.project.interfaces;

import com.skillify.project.model.Course;

import java.util.List;

public interface StudentDashboardService {
    List<Course> getEnrolledCourses(Long studentId) throws Exception;
    void markLessonAsCompleted(Long lessonProgressId) throws Exception;
}

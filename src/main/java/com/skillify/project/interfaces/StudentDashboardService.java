package com.skillify.project.interfaces;

import com.skillify.project.model.Course;

import java.util.List;

public interface StudentDashboardService {
    List<Course> getEnrolledCourses(String studentId) throws Exception;
    void markLessonAsCompleted(String lessonProgressId) throws Exception;
}

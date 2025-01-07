package com.skillify.project.interfaces;

import com.skillify.project.model.Enrollment;

import java.util.List;

public interface StudentDashboardService {
    List<Enrollment> getEnrolledCourses(Long studentId) throws Exception;
    void markLessonAsCompleted(Long lessonProgressId) throws Exception;
}

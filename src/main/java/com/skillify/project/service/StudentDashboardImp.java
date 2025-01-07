package com.skillify.project.service;

import com.skillify.project.interfaces.StudentDashboardService;
import com.skillify.project.model.Enrollment;
import com.skillify.project.model.Lesson;
import com.skillify.project.repository.EnrollmentRepository;
import com.skillify.project.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDashboardImp implements StudentDashboardService {
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    public StudentDashboardImp(EnrollmentRepository enrollmentRepository, LessonRepository lessonRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Enrollment> getEnrolledCourses(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public void markLessonAsCompleted(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        lesson.setStatus(true);
        lessonRepository.save(lesson);
    }

}

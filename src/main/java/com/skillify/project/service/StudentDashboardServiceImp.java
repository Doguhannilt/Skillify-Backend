package com.skillify.project.service;

import com.skillify.project.interfaces.StudentDashboardService;
import com.skillify.project.model.Course;
import com.skillify.project.model.Enrollment;
import com.skillify.project.model.Lesson;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.EnrollmentRepository;
import com.skillify.project.repository.LessonRepository;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class StudentDashboardServiceImp implements StudentDashboardService {
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public StudentDashboardServiceImp(EnrollmentRepository enrollmentRepository, LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;

    }

    @Override
    public List<Course> getEnrolledCourses(String studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        return enrollments.stream()
                .map(enrollment -> courseRepository.findById(String.valueOf(enrollment.getCourseId())).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void markLessonAsCompleted(String lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        lesson.setStatus(true);
        lessonRepository.save(lesson);
    }
}

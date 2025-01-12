package com.skillify.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@Document
public class Enrollment {

    @Id
    private Long id;

    private Long courseId;

    private Long studentId;

    private LocalDate enrollmentDate;

    private static Map<Long, Boolean> lessonCompletionStatus; // Ders ID ve tamamlanma durumu


    public static Map<Long, Boolean> getLessonCompletionStatus() {
        return lessonCompletionStatus;
    }

    public void setLessonCompletionStatus(Map<Long, Boolean> lessonCompletionStatus) {
        this.lessonCompletionStatus = lessonCompletionStatus;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

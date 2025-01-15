package com.skillify.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@Document
public class Enrollment {

    @Id
    private String id;

    private String courseId;

    private String studentId;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

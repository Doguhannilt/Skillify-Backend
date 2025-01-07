package com.skillify.project.interfaces;

import com.skillify.project.model.Lesson;
import org.springframework.http.ResponseEntity;

public interface LessonService {
    ResponseEntity<String> createLesson(Lesson lesson) throws Exception;
    ResponseEntity<String> deleteLesson(Lesson lesson) throws Exception;
    ResponseEntity<String> updateLesson(Lesson lesson) throws Exception;

}

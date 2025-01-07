package com.skillify.project.interfaces;

import com.skillify.project.model.Lesson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface LessonService {
    ResponseEntity<String> createLesson(Lesson lesson, MultipartFile videoFile) throws Exception;
    ResponseEntity<String> deleteLesson(Lesson lesson) throws Exception;
    ResponseEntity<String> updateLesson(Lesson lesson,MultipartFile videoFile) throws Exception;

}

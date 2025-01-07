package com.skillify.project.controller;

import com.skillify.project.interfaces.LessonService;
import com.skillify.project.model.Lesson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createLesson(
            @ModelAttribute Lesson lesson,
            @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try {
            return lessonService.createLesson(lesson, videoFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        try {
            Lesson lesson = new Lesson();
            lesson.setId(id);
            return lessonService.deleteLesson(lesson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateLesson(
            @ModelAttribute Lesson lesson,
            @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try {
            return lessonService.updateLesson(lesson, videoFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

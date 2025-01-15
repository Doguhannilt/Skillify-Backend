package com.skillify.project.controller;

import com.skillify.project.interfaces.LessonService;
import com.skillify.project.model.Lesson;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    private static final Logger logger = LoggerFactory.getLogger(LessonController.class);
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // Create a lesson
    @Operation(summary = "Create a lesson", description = "Create a new lesson with optional video file")
    @PostMapping("/create")
    public ResponseEntity<String> createLesson(
            @ModelAttribute Lesson lesson,
            @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try {
            logger.info("Creating lesson with title: {}", lesson.getTitle());
            ResponseEntity<String> response = lessonService.createLesson(lesson, videoFile);
            logger.info("Lesson created successfully with title: {}", lesson.getTitle());
            return response;
        } catch (Exception e) {
            logger.error("Error creating lesson with title: {}", lesson.getTitle(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Delete a lesson by id
    @Operation(summary = "Delete a lesson by id", description = "Delete a lesson by its ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable String id) {
        try {
            logger.info("Deleting lesson with ID: {}", id);
            Lesson lesson = new Lesson();
            lesson.setId(id);
            ResponseEntity<String> response = lessonService.deleteLesson(lesson);
            logger.info("Lesson with ID: {} deleted successfully", id);
            return response;
        } catch (Exception e) {
            logger.error("Error deleting lesson with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update a lesson
    @Operation(summary = "Update a lesson", description = "Update an existing lesson with optional video file")
    @PutMapping("/update")
    public ResponseEntity<String> updateLesson(
            @ModelAttribute Lesson lesson,
            @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        try {
            logger.info("Updating lesson with ID: {}", lesson.getId());
            ResponseEntity<String> response = lessonService.updateLesson(lesson, videoFile);
            logger.info("Lesson with ID: {} updated successfully", lesson.getId());
            return response;
        } catch (Exception e) {
            logger.error("Error updating lesson with ID: {}", lesson.getId(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

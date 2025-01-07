package com.skillify.project.service;

import com.skillify.project.interfaces.LessonService;
import com.skillify.project.model.Course;
import com.skillify.project.model.Lesson;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class LessonServiceImp implements LessonService {

    private final CloudinaryService cloudinaryService;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public LessonServiceImp(CloudinaryService cloudinaryService, CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.cloudinaryService = cloudinaryService;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public ResponseEntity<String> createLesson(Lesson lesson, MultipartFile videoFile) throws Exception {
        try {
            // Course ID kontrolü
            Optional<Course> isCourseIdValid = courseRepository.findById(String.valueOf(lesson.getCourseId()));
            if (isCourseIdValid.isEmpty()) {
                throw new IllegalArgumentException("Course is not available");
            }

            // Video dosyası yükleme
            if (videoFile != null && !videoFile.isEmpty()) {
                String videoUrl = cloudinaryService.uploadVideo(videoFile);
                lesson.setVideoUrl(videoUrl);
            }

            // Lesson kaydetme
            lessonRepository.save(lesson);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Lesson has been created with ID: " + lesson.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteLesson(Lesson lesson) throws Exception {
        try {
            lessonRepository.deleteById(lesson.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Course has been created: " + lesson.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> updateLesson(Lesson lesson, MultipartFile videoFile) throws Exception {
        try {
            Lesson existingLesson = lessonRepository.findById(lesson.getId())
                    .orElseThrow(() -> new Exception("Lesson not found with ID: " + lesson.getId()));
            existingLesson.setTitle(lesson.getTitle());
            existingLesson.setContent(lesson.getContent());
            existingLesson.setCourseId(lesson.getCourseId());
            if (videoFile != null && !videoFile.isEmpty()) {
                String videoUrl = cloudinaryService.uploadVideo(videoFile);
                existingLesson.setVideoUrl(videoUrl);
            }
            lessonRepository.save(existingLesson);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Lesson updated successfully with ID: " + existingLesson.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

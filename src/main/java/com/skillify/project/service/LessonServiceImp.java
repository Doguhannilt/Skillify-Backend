package com.skillify.project.service;

import com.skillify.project.interfaces.LessonService;
import com.skillify.project.model.Course;
import com.skillify.project.model.Lesson;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonServiceImp implements LessonService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public LessonServiceImp(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public ResponseEntity<String> createLesson(Lesson lesson) throws Exception {
        try {
            Optional<Course> isCourseIdValid = courseRepository.findById(String.valueOf(lesson.getCourseId()));
            if(isCourseIdValid.isEmpty()){throw new IllegalArgumentException("Course is not available");}
            lessonRepository.save(lesson);
            return ResponseEntity.status(HttpStatus.CREATED).body("Course has been created: " + lesson.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteLesson(Lesson lesson) throws Exception {
        try {
            courseRepository.deleteById(String.valueOf(lesson.getId()));
            return ResponseEntity.status(HttpStatus.CREATED).body("Course has been created: " + lesson.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> updateLesson(Lesson lesson) throws Exception {
        try{
            if (lesson == null || lesson.getId() == null) {throw new IllegalArgumentException("Lesson or Lesson ID cannot be null");}

            Optional<Lesson> existingLesson = lessonRepository.findById(lesson.getId());
            if (existingLesson.isEmpty()) {throw new Exception("Lesson not found with ID: " + lesson.getId());}

            Lesson updatedLesson = existingLesson.get();
            updatedLesson.setTitle(lesson.getTitle());
            updatedLesson.setContent(lesson.getContent());
            updatedLesson.setVideoUrl(lesson.getVideoUrl());
            updatedLesson.setCourseId(lesson.getCourseId());

            lessonRepository.save(updatedLesson);

            return ResponseEntity.status(HttpStatus.OK).body("Lesson updated successfully with ID: " + updatedLesson.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }

    }
}

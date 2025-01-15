package com.skillify.project.service;


import com.skillify.project.model.Course;
import com.skillify.project.model.Lesson;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.LessonRepository;
import com.skillify.project.repository.UserRepository;
import com.skillify.project.utils.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @InjectMocks
    private LessonServiceImp lessonService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private MailSender mailSender;

    private Lesson lesson;
    private Course course;
    private User instructor;
    private MultipartFile videoFile;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        lesson.setId("1L");
        lesson.setCourseId("100L");

        course = new Course();
        course.setId("100L");
        course.setInstructorId("200L");

        instructor = new User();
        instructor.setId("200L");
        instructor.setEmail("instructor@example.com");

        videoFile = mock(MultipartFile.class);
    }


    @Test
    void createLesson_Success() throws Exception {
        // Arrange
        when(videoFile.isEmpty()).thenReturn(false);
        when(courseRepository.findById("100")).thenReturn(Optional.of(course));
        when(userRepository.findById("200L")).thenReturn(Optional.of(instructor));
        when(cloudinaryService.uploadVideo(videoFile)).thenReturn("http://video.url");
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        // Act
        ResponseEntity<String> response = lessonService.createLesson(lesson, videoFile);
        System.out.println(response.getBody());

        // Assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals("Lesson has been created with ID: " + lesson.getId(), response.getBody());
        verify(courseRepository, times(1)).save(course);
        verify(mailSender, times(1))
                .sendEmailToInstructor(eq(Optional.of(instructor)), eq(course), eq("instructor@example.com"), eq("Lesson is created"));
    }

    @Test
    void createLesson_CourseNotFound() {
        // Arrange
        when(courseRepository.findById("100")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = assertDoesNotThrow(() -> lessonService.createLesson(lesson, videoFile));

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Course is not available"));
    }

    @Test
    void createLesson_InstructorNotFound() {
        // Arrange
        when(courseRepository.findById("100")).thenReturn(Optional.of(course));
        when(userRepository.findById("200L")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = assertDoesNotThrow(() -> lessonService.createLesson(lesson, videoFile));

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Instructor not found"));
    }

    @Test
    void createLesson_VideoUploadFails() throws Exception {
        // Arrange
        when(courseRepository.findById("100")).thenReturn(Optional.of(course));
        when(userRepository.findById("200L")).thenReturn(Optional.of(instructor));
        when(cloudinaryService.uploadVideo(videoFile)).thenThrow(new RuntimeException("Video upload failed"));

        // Act
        ResponseEntity<String> response = assertDoesNotThrow(() -> lessonService.createLesson(lesson, videoFile));

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Video upload failed"));
    }

    @Test
    void deleteLesson_Success() throws Exception {

        Lesson lesson = new Lesson();
        lesson.setId("1L"); // Silinecek lesson ID'si


        doNothing().when(lessonRepository).deleteById(lesson.getId());


        ResponseEntity<String> response = lessonService.deleteLesson(lesson);

        // Assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Course has been created: " + lesson.getId())); // Mesaj kontrolü
        verify(lessonRepository, times(1)).deleteById(lesson.getId()); // deleteById metodu bir kez çağrılmalı
    }

    @Test
    void updateLesson_Success() throws Exception {
        // Arrange
        Lesson lessonToUpdate = new Lesson();
        lessonToUpdate.setId("1L");
        lessonToUpdate.setTitle("New Title");
        lessonToUpdate.setContent("Updated content");
        lessonToUpdate.setCourseId("100L");

        Lesson existingLesson = new Lesson();
        existingLesson.setId("1L");
        existingLesson.setTitle("Old Title");
        existingLesson.setContent("Old content");
        existingLesson.setCourseId("100L");

        // Mocking
        when(lessonRepository.findById(lessonToUpdate.getId())).thenReturn(Optional.of(existingLesson)); // Var olan lesson'ı bul

        when(lessonRepository.save(existingLesson)).thenReturn(existingLesson);

        // Act
        ResponseEntity<String> response = lessonService.updateLesson(lessonToUpdate, null); // videoFile null

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Lesson updated successfully with ID: " + existingLesson.getId()));
        verify(lessonRepository, times(1)).save(existingLesson); // save metodu bir kez çağrılmalı
    }

    @Test
    void updateLesson_LessonNotFound() throws Exception {
        // Arrange
        Lesson lessonToUpdate = new Lesson();
        lessonToUpdate.setId("1L");
        lessonToUpdate.setTitle("New Title");

        // Mocking
        when(lessonRepository.findById(lessonToUpdate.getId())).thenReturn(Optional.empty()); // Lesson bulunamadığında empty dönecek

        // Act
        ResponseEntity<String> response = lessonService.updateLesson(lessonToUpdate, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Lesson not found with ID: " + lessonToUpdate.getId())); // Hata mesajı
    }
}

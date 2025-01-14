package com.skillify.project.service;

import com.skillify.project.model.Course;
import com.skillify.project.model.User;
import com.skillify.project.repository.CourseRepository;
import com.skillify.project.repository.UserRepository;
import com.skillify.project.utils.MailSender;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private CourseServiceImp courseService;

    private Course course;
    private User user;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock objects
        course = new Course();
        course.setId(12L);
        course.setName("Java Programming");
        course.setInstructorId(12L);  // Valid instructorId as Long

        user = new User();
        user.setId(12L);  // Ensure instructor has the same ID as in the course
        user.setEmail("instructor@example.com");
    }

    @Test
    void createCourse_Success() throws Exception {
        // Arrange
        Long validInstructorId = 12L;
        course.setInstructorId(validInstructorId);

        // Mocking
        when(courseRepository.findByName(course.getName())).thenReturn(Optional.empty());
        when(userRepository.findById(validInstructorId)).thenReturn(Optional.of(user));  // Mock correct ID with instructor

        // Act
        Course createdCourse = courseService.createCourse(course);

        // Assert
        verify(userRepository).findById(validInstructorId);  // Verify that findById is called with the correct ID
        assertNotNull(createdCourse);
        assertEquals(course.getName(), createdCourse.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
        verify(mailSender, times(1)).sendEmailToInstructor(eq(Optional.of(user)), eq(course),
                eq(user.getEmail()), eq("Your course has been successfully created."));
    }

    @Test
    void createCourse_CourseAlreadyExists() throws Exception {
        // Arrange
        when(courseRepository.findByName(course.getName())).thenReturn(Optional.of(course));

        // Act & Assert
        assertThrows(Exception.class, () -> courseService.createCourse(course));
    }

    @Test
    void deleteCourse_Success() throws Exception {
        // Arrange
        when(courseRepository.findById(String.valueOf(course.getId()))).thenReturn(Optional.of(course));

        // Act
        ResponseEntity<String> response = courseService.deleteCourse(course);

        // Assert
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Course has been deleted"));
        verify(courseRepository, times(1)).deleteById(any());
        verify(mailSender, times(1)).sendEmailToInstructor(eq(Optional.of(user)), eq(course),
                eq(user.getEmail()), eq("Your course has been deleted."));
    }

    @Test
    void deleteCourse_CourseNotFound() throws Exception {
        // Arrange
        when(courseRepository.findById(String.valueOf(course.getId()))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = courseService.deleteCourse(course);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Course not found"));
    }

    @Test
    void updateCourse_Success() {
        // Arrange
        when(courseRepository.findById(String.valueOf(course.getId()))).thenReturn(Optional.of(course));

        Course updatedCourse = new Course();
        updatedCourse.setId(course.getId());
        updatedCourse.setName("Advanced Java Programming");

        // Act
        ResponseEntity<String> response = courseService.updateCourse(updatedCourse);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Course updated successfully"));
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse_CourseNotFound() {
        // Arrange
        when(courseRepository.findById(String.valueOf(course.getId()))).thenReturn(Optional.empty());

        Course updatedCourse = new Course();
        updatedCourse.setId(course.getId());
        updatedCourse.setName("Advanced Java Programming");

        // Act
        ResponseEntity<String> response = courseService.updateCourse(updatedCourse);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Course not found"));
    }
}
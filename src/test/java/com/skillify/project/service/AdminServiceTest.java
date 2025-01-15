package com.skillify.project.service;

import com.skillify.project.model.*;
import com.skillify.project.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private AdminServiceImp adminService;

    private User user;
    private Course course;
    private Enrollment enrollment;
    private Lesson lesson;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setLastLogin(LocalDate.now());

        course = new Course();
        course.setId("1");
        course.setName("Java Programming");

        lesson = new Lesson();
        lesson.setId("1");
        lesson.setTitle("Introduction to Java");

        enrollment = new Enrollment();
        enrollment.setCourseId("1");
        enrollment.setId("1");
        enrollment.setEnrollmentDate(LocalDate.now());
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        ResponseEntity<List<User>> response = adminService.getAllUsers();

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(user.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetUserById() throws Exception {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        ResponseEntity<Optional<User>> response = adminService.getUserById(user);

        assertNotNull(response);
        assertTrue(response.getBody().isPresent());
        assertEquals(user.getId(), response.getBody().get().getId());
    }

    @Test
    void testGetAllCourses() throws Exception {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        ResponseEntity<List<Course>> response = adminService.getAllCourses(course);

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(course.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetCourseById() throws Exception {
        when(courseRepository.findById("1")).thenReturn(Optional.of(course));

        ResponseEntity<Course> response = adminService.getCourseById(course);

        assertNotNull(response);
        assertEquals(course.getId(), response.getBody().getId());
    }

    @Test
    void testGetPopularCourses() throws Exception {
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment));
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        ResponseEntity<List<Course>> response = adminService.getPopularCourses();

        assertNotNull(response);
        assertTrue(response.getBody().contains(course));
    }

    @Test
    void testGetActiveUsers() throws Exception {
        when(userRepository.findByLastLoginAfter(LocalDate.now().minusDays(10))).thenReturn(Arrays.asList(user));

        ResponseEntity<List<User>> response = adminService.getActiveUsers();

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(user.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetEnrollmentsWithinRange() throws Exception {
        when(enrollmentRepository.findByEnrollmentDateBetween(LocalDate.now().minusDays(1), LocalDate.now())).thenReturn(Arrays.asList(enrollment));

        ResponseEntity<List<Enrollment>> response = adminService.getEnrollmentsWithinRange(LocalDate.now().minusDays(1), LocalDate.now());

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(enrollment.getCourseId(), response.getBody().get(0).getCourseId());
    }
}

package com.skillify.project.service;

import com.skillify.project.model.Course;
import com.skillify.project.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstructorDashboardServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private InstructorDashboardServiceImp courseService;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        course1 = new Course();
        course1.setId("1");
        course1.setName("Course 1");
        course1.setInstructorId("instructor1");

        course2 = new Course();
        course2.setId("2");
        course2.setName("Course 2");
        course2.setInstructorId("instructor1");
    }

    @Test
    void testGetInstructorCourses_Success() throws Exception {
        when(courseRepository.findByInstructorId("instructor1")).thenReturn(Arrays.asList(course1, course2));
        List<Course> courses = courseService.getInstructorCourses("instructor1");
        assertNotNull(courses);
        assertEquals(2, courses.size());  // 2 kurs dönmeli
        assertEquals("Course 1", courses.get(0).getName());
        assertEquals("Course 2", courses.get(1).getName());
        verify(courseRepository, times(1)).findByInstructorId("instructor1");
    }

    @Test
    void testGetInstructorCourses_NoCourses() throws Exception {
        when(courseRepository.findByInstructorId("instructor2")).thenReturn(Arrays.asList());
        List<Course> courses = courseService.getInstructorCourses("instructor2");
        assertNotNull(courses);
        assertTrue(courses.isEmpty());  // Boş liste dönmeli
        verify(courseRepository, times(1)).findByInstructorId("instructor2");
    }

    @Test
    void testGetInstructorCourses_Exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> courseService.getInstructorCourses(null));
        assertEquals("Instructor ID cannot be null.", exception.getMessage());
    }
}

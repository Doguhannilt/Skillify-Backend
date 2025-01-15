package com.skillify.project.service;

import com.skillify.project.model.Course;
import com.skillify.project.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SearchServiceImp searchService;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        // Örnek Course nesneleri oluşturuluyor
        course1 = new Course();
        course1.setId("1");
        course1.setName("Java Programming");

        course2 = new Course();
        course2.setId("2");
        course2.setName("Spring Boot Basics");
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = Arrays.asList(course1, course2);

        when(courseRepository.findAll()).thenReturn(courses);

        ResponseEntity<List<Course>> response = searchService.getAllCourses();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testFilterByName_Success() {
        List<Course> courses = Arrays.asList(course1);

        when(courseRepository.findByNameContainingIgnoreCase("Java")).thenReturn(courses);

        ResponseEntity<List<Course>> response = searchService.filterByName("Java");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Java Programming", response.getBody().get(0).getName());
        verify(courseRepository, times(1)).findByNameContainingIgnoreCase("Java");
    }

    @Test
    void testFilterByInstructorName_Success() {
        List<Course> courses = Arrays.asList(course2);

        when(courseRepository.findByNameContainingIgnoreCase("Jane")).thenReturn(courses);

        ResponseEntity<List<Course>> response = searchService.filterByInstructorName("Jane");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Spring Boot Basics", response.getBody().get(0).getName());
        verify(courseRepository, times(1)).findByNameContainingIgnoreCase("Jane");
    }

    @Test
    void testFilterByName_NoCoursesFound() {
        when(courseRepository.findByNameContainingIgnoreCase("NonExistentCourse")).thenReturn(Arrays.asList());

        ResponseEntity<List<Course>> response = searchService.filterByName("NonExistentCourse");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(courseRepository, times(1)).findByNameContainingIgnoreCase("NonExistentCourse");
    }

    @Test
    void testFilterByInstructorName_NoCoursesFound() {
        when(courseRepository.findByNameContainingIgnoreCase("NonExistentInstructor")).thenReturn(Arrays.asList());

        ResponseEntity<List<Course>> response = searchService.filterByInstructorName("NonExistentInstructor");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(courseRepository, times(1)).findByNameContainingIgnoreCase("NonExistentInstructor");
    }
}

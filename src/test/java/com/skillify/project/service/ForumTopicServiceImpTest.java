package com.skillify.project.service;

import com.skillify.project.model.ForumTopic;
import com.skillify.project.model.Course;
import com.skillify.project.repository.ForumTopicRepository;
import com.skillify.project.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForumTopicServiceImpTest {

    @Mock
    private ForumTopicRepository forumTopicRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ForumTopicServiceImp forumTopicService;

    private ForumTopic forumTopic;
    private Course course;

    @BeforeEach
    void setUp() {
        forumTopic = new ForumTopic();
        forumTopic.setId("1L");
        forumTopic.setTitle("Test Topic");
        forumTopic.setDescription("Test Description");
        forumTopic.setInstructorId("1L");

        course = new Course();
        course.setInstructorId("1L");
        course.setName("Test Course");

    }

    @Test
    void testGetAllForumTopics_NoTopics() throws Exception {
        when(forumTopicRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> forumTopicService.getAllForumTopics());

        assertEquals("No forum topics found.", exception.getMessage());
    }

    @Test
    void testGetAllForumTopics_Success() throws Exception {
        List<ForumTopic> forumTopics = Collections.singletonList(forumTopic);
        when(forumTopicRepository.findAll()).thenReturn(forumTopics);

        List<ForumTopic> result = forumTopicService.getAllForumTopics();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(forumTopic, result.get(0));
    }

    @Test
    void testCreateForumTopic_EmptyTitle() {
        forumTopic.setTitle("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> forumTopicService.createForumTopic(forumTopic));

        assertEquals("Forum topic title cannot be empty.", exception.getMessage());
    }

    @Test
    void testCreateForumTopic_EmptyDescription() {
        forumTopic.setDescription("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> forumTopicService.createForumTopic(forumTopic));

        assertEquals("Forum topic description cannot be empty.", exception.getMessage());
    }

    @Test
    void testCreateForumTopic_NullInstructorId() {
        forumTopic.setInstructorId(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> forumTopicService.createForumTopic(forumTopic));

        assertEquals("Instructor ID cannot be null.", exception.getMessage());
    }

    @Test
    void testCreateForumTopic_InstructorNotAvailable() throws Exception {
        when(courseRepository.findByInstructorId(forumTopic.getInstructorId())).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(Exception.class, () -> forumTopicService.createForumTopic(forumTopic));

        assertEquals("Instructor is not available or does not have any courses.", exception.getMessage());
    }

    @Test
    void testCreateForumTopic_Success() throws Exception {
        when(courseRepository.findByInstructorId(forumTopic.getInstructorId())).thenReturn(Collections.singletonList(course));
        when(forumTopicRepository.save(forumTopic)).thenReturn(forumTopic);

        ForumTopic createdForumTopic = forumTopicService.createForumTopic(forumTopic);

        assertNotNull(createdForumTopic);
        assertEquals(forumTopic, createdForumTopic);
    }
}

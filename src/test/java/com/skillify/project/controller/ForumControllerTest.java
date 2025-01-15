package com.skillify.project.controller;



import com.skillify.project.model.ForumTopic;
import com.skillify.project.service.ForumTopicServiceImp;
import com.skillify.project.service.ForumTopicServiceImpTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ForumControllerTest {

    @Mock
    private ForumTopicServiceImp forumTopicService;

    @InjectMocks
    private ForumController forumController;

    @Mock
    private CacheManager cacheManager;

    private ForumTopic forumTopic;

    @BeforeEach
    void setUp() {
        forumTopic = new ForumTopic();
        forumTopic.setId("1L");
        forumTopic.setTitle("Test Topic");
        forumTopic.setDescription("Test Description");
    }

    @Test
    void testGetAllForumTopics() throws Exception {
        List<ForumTopic> forumTopics = Collections.singletonList(forumTopic);
        when(forumTopicService.getAllForumTopics()).thenReturn(forumTopics);

        ResponseEntity<List<ForumTopic>> response = forumController.getAllForumTopics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(forumTopics, response.getBody());
    }

    @Test
    void testCreateForumTopic() throws Exception {
        when(forumTopicService.createForumTopic(forumTopic)).thenReturn(forumTopic);

        ResponseEntity<ForumTopic> response = forumController.createForumTopic(forumTopic);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(forumTopic, response.getBody());
    }
}

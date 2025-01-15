package com.skillify.project.controller;

import com.skillify.project.model.ForumTopic;
import com.skillify.project.service.ForumTopicServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumTopicServiceImp forumTopicService;

    public ForumController(ForumTopicServiceImp forumTopicService) {
        this.forumTopicService = forumTopicService;
    }


    @GetMapping("/topics")
    @Operation(summary = "Get All Forum Topics")
    @Cacheable(value = "forumTopicsCache", key = "'allTopics'")
    public ResponseEntity<List<ForumTopic>> getAllForumTopics() throws Exception {
        return ResponseEntity.ok(forumTopicService.getAllForumTopics());
    }

    @PostMapping("/topics")
    @Operation(summary = "Create Forum Topic")
    @CachePut(value = "forumTopicsCache", key = "#forumTopic.id")
    public ResponseEntity<ForumTopic> createForumTopic(@RequestBody ForumTopic forumTopic) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(forumTopicService.createForumTopic(forumTopic));
    }
}

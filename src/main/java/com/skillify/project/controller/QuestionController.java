package com.skillify.project.controller;

import com.skillify.project.model.Question;
import com.skillify.project.service.QuestionServiceImp;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/forum/questions")
public class QuestionController {

    private final QuestionServiceImp questionService;

    public QuestionController(QuestionServiceImp questionService) {
        this.questionService = questionService;
    }


    @GetMapping("/topic/{topicId}")
    @Operation(summary = "Get Questions by Forum Topic", description = "Fetches all questions related to the specified forum topic. Caches the result for faster access.")
    @Cacheable(value = "questionsCache", key = "#topicId")
    public ResponseEntity<List<Question>> getQuestionsByForumTopic(@PathVariable String topicId) throws Exception {
        return ResponseEntity.ok(questionService.getQuestionsByForumTopicId(topicId));
    }

    @PostMapping
    @Operation(summary = "Ask a Question", description = "Allows a student to ask a question related to a forum topic. The question is stored in the database.")
    public ResponseEntity<Question> askQuestion(@RequestBody Question question) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.askQuestion(question));
    }
}

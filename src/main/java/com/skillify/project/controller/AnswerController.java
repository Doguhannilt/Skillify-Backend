package com.skillify.project.controller;

import com.skillify.project.model.Answer;
import com.skillify.project.service.AnswerServiceImp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forum/answers")
public class AnswerController {

    private final AnswerServiceImp answerService;

    public AnswerController(AnswerServiceImp answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @Operation(summary = "Answer a Question", description = "Provides an answer to a specific question. The answer will be saved in the database.")
    public ResponseEntity<Answer> answerQuestion(@RequestBody Answer answer) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.answerQuestion(answer));
    }
}


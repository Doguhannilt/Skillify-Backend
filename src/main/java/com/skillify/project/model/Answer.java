package com.skillify.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "answers")
public class Answer {
    @Id
    private Long id;
    private String content;
    private Long instructorId;
    private Long questionId;
    private int likesCount = 0;
    private String status; // pending, answered, liked
    private List<Comment> comments = new ArrayList<>();
}

package com.skillify.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String content;
    private Long userId;
    private String answerId;
    private Date date;
    private int likesCount = 0;
}

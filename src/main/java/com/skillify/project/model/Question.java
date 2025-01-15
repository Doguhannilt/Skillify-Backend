package com.skillify.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "questions")
public class Question {
    @Id
    private Long id;
    private String content;
    private Long studentId;
    private Long forumTopicId;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getForumTopicId() {
        return forumTopicId;
    }

    public void setForumTopicId(Long forumTopicId) {
        this.forumTopicId = forumTopicId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

package com.skillify.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Favorite {
    @Id
    private String id;
    @DBRef(lazy = true)
    private List<Course> courseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Course> getCourseId() {
        return courseId;
    }

    public void setCourseId(List<Course> courseId) {
        this.courseId = courseId;
    }
}

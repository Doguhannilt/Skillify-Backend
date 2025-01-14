package com.skillify.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class Course {

    @Id
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private List<String> language;

    private Status status;

    private Long instructorId;

    @JsonIgnore
    private CompletionStatus completionStatus;

    private List<Long> lessonIds;

    private boolean isPurchased;

    public Course() {
        this.lessonIds = new ArrayList<>();  // Constructor'da başlatıyoruz
    }

    public Course(Long id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.language = new ArrayList<>();  // Optional, eğer dil listesi varsa başlatılır
        this.status = Status.AVAILABLE;  // Varsayılan bir statü atayabilirsiniz
        this.lessonIds = new ArrayList<>();
        this.isPurchased = false;  // Varsayılan olarak false
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }


    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public List<Long> getLessonIds() {
        return lessonIds;
    }

    public void setLessonIds(List<Long> lessonIds) {
        this.lessonIds = lessonIds;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

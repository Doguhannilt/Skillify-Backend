package com.skillify.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Badge {
    @Id
    private String id;
    private String name;
    private String description;
    private String iconUrl;
    private Long pointsRequired;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(Long pointsRequired) {
        this.pointsRequired = pointsRequired;
    }
}

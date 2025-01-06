package com.skillify.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ROLE_ADMIN, ROLE_INSTRUCTOR, ROLE_STUDENT;

    @JsonCreator
    public static Role fromString(String value) {
        for (Role role : values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}

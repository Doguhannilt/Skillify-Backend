package com.skillify.project.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SignupRequest {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String role;  // ROLE_ADMIN, ROLE_INSTRUCTOR, ROLE_STUDENT gibi değerler

    // Getter ve Setter metodları

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
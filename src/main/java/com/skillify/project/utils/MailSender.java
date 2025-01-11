package com.skillify.project.utils;

import com.skillify.project.model.Course;
import com.skillify.project.model.User;
import com.skillify.project.service.EmailServiceImp;

import java.time.LocalDate;
import java.util.Optional;

public class MailSender {
    private final EmailServiceImp emailServiceImp;

    public MailSender(EmailServiceImp emailServiceImp) {
        this.emailServiceImp = emailServiceImp;
    }

    public void sendEmailToInstructor(Optional<User> instructor, Course course, String instructorEmail, String description) {
        LocalDate date = LocalDate.now();
        String subject =  "Notification - Skillify";
        String body ="Dear " + instructor.get().getName() + ",\n\n"
                +  description + "-" + course.getName() + ".\n"
                + "Thank you,\n"
                + "Your Online Course Platform \n"
                + "Date: " + date;
        emailServiceImp.sendEmail(instructorEmail, subject, body);
    }
}



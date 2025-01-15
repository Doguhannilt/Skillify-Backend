package com.skillify.project.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp {
    private final JavaMailSender mailSender;
    public EmailServiceImp(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendEmail(String to, String subject, String body) {
        if (to == null || subject == null || body == null) {
            throw new IllegalArgumentException("Email parameters cannot be null.");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("doguhannilt@gmail.com");
        mailSender.send(message);
    }
}
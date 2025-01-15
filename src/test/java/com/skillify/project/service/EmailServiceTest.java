package com.skillify.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImp emailService;

    private String to;
    private String subject;
    private String body;

    @BeforeEach
    void setUp() {
        to = "test@example.com";
        subject = "Test Subject";
        body = "This is a test email body.";
    }

    @Test
    void testSendEmail() {
        emailService.sendEmail(to, subject, body);

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(body);
        expectedMessage.setFrom("doguhannilt@gmail.com");

        verify(mailSender, times(1)).send(expectedMessage);
    }

    @Test
    void testSendEmail_NullValues() {
        // Null değerler verildiğinde IllegalArgumentException bekliyoruz
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(null, null, null));

        // Exception mesajını doğruluyoruz
        assertEquals("Email parameters cannot be null.", exception.getMessage());

        // JavaMailSender'ın send metodu çağrılmamalı
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

}

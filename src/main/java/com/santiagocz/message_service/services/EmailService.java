package com.santiagocz.message_service.services;

import com.santiagocz.message_service.classes.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void scheduleEmail(EmailRequest emailRequest) {
        long delay = calculateDelay(emailRequest.getSendAt());
        scheduler.schedule(() -> {
            sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        }, delay, TimeUnit.MILLISECONDS);
    }

    private long calculateDelay(LocalDateTime sendAt) {
        return java.time.Duration.between(LocalDateTime.now(), sendAt).toMillis();
    }
}


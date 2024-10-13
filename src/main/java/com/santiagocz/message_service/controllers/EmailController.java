package com.santiagocz.message_service.controllers;

import com.santiagocz.message_service.classes.EmailRequest;
import com.santiagocz.message_service.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody @Valid EmailRequest request) {
        LocalDateTime sendAt = request.getSendAt();

        if (sendAt != null && sendAt.isAfter(LocalDateTime.now())) {
            emailService.scheduleEmail(request); // Method to schedule the email sending
            return "Email scheduled to be sent at " + sendAt;
        } else {
            emailService.sendSimpleEmail(request.getTo(), request.getSubject(), request.getBody());
            return "Email sent immediately";
        }
    }
}

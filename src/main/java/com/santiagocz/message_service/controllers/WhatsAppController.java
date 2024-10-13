package com.santiagocz.message_service.controllers;

import com.santiagocz.message_service.classes.EmailRequest;
import com.santiagocz.message_service.classes.WhatsAppRequest;
import com.santiagocz.message_service.services.WhatsAppService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    @Autowired
    private WhatsAppService whatsappService;

    @PostMapping("/send-whatsapp")
    public String sendEmail(@RequestBody @Valid WhatsAppRequest request) {
        LocalDateTime sendAt = request.getSendAt();

        if (sendAt != null && sendAt.isAfter(LocalDateTime.now())) {
            whatsappService.scheduleWhatsAppMessage(request); // Method to schedule the whatsapp sending
            return "WhatsApp scheduled to be sent at " + sendAt;
        } else {
            whatsappService.sendWhatsAppMessage(request.getTo(), request.getMessage());
            return "Whatsapp sent immediately";
        }
    }
}


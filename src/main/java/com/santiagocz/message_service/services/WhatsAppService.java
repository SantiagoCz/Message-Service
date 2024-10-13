package com.santiagocz.message_service.services;

import com.santiagocz.message_service.classes.WhatsAppRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class WhatsAppService {

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public void sendWhatsAppMessage(String to, String message) {
        WhatsAppRequest request = WhatsAppRequest.builder()
                .to(to)
                .message(message)
                .build();
        sendMessage(request);
    }

    public void scheduleWhatsAppMessage(WhatsAppRequest request) {
        long delay = Duration.between(LocalDateTime.now(), request.getSendAt()).toMillis();
        scheduler.schedule(() -> sendMessage(request), delay, TimeUnit.MILLISECONDS);
    }

    private void sendMessage(WhatsAppRequest request) {
        String requestBody = String.format("{\"to\":\"%s\",\"message\":\"%s\"}", request.getTo(), request.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(apiUrl, entity, String.class);
    }
}

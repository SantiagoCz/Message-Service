package com.santiagocz.message_service.classes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @NotBlank(message = "The recipient email address is required.")
    @Email(message = "Invalid email format.")
    private String to;

    @NotBlank(message = "The subject is required.")
    @Size(max = 100, message = "The subject must be less than 100 characters.")
    private String subject;

    @Size(max = 500, message = "The body must be less than 500 characters.")
    private String body;

    private LocalDateTime sendAt;

}


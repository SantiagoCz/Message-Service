package com.santiagocz.message_service.classes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppRequest {

    @NotBlank(message = "Recipient's phone number cannot be blank")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String to; // Recipient's phone number

    @NotBlank(message = "Message cannot be blank")
    private String message; // Message to be sent

    private LocalDateTime sendAt;

}



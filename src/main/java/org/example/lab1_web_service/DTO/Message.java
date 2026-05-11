package org.example.lab1_web_service.DTO;

public record Message(
        String role,
        String content,
        String sessionId) {
}

package org.example.lab1_web_service.DTO;

import java.util.List;

public record ChatRequests(
        String sessionId,
        String personality,
        String message
) {
}

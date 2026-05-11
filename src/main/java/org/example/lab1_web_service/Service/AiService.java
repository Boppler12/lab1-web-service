package org.example.lab1_web_service.Service;

import org.example.lab1_web_service.DTO.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AiService {
    private RestClient restClient;

    public AiService(RestClient.Builder builder, @Value("${ai.api.key}") String apiKey) {
        this.restClient = builder
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public String getAiResponse(String personality, String userMessage, List<Message> history){
        // ska bestäma hur system promptar baserat på personligttype
        String systemPrompt = switch (personality.toLowerCase()) {
            case "coder"-> "You are a helpful assistant that codes.";
            case "pirate" -> "I will tell you a pirate story.";
            case "helper" -> "I am a helpful assistant that helps you with your tasks.";
            default -> "You are a helpful assistant.";
        };

        // 2. Förbered meddelanden för AI:n (System Prompt + Historik)
        // OpenRouter förväntar sig en lista med {role: ..., content: ...}
        List<Map<String, String>> messages = new ArrayList<>();        messages.add(Map.of("role", "system", "content", systemPrompt));

        for (Message msg : history) {
            messages.add(Map.of("role", msg.role(), "content", msg.content()));
        }

        // 3. Bygg Request Body
        Map<String, Object> requestBody = Map.of(
                "model", "openai/gpt-3.5-turbo", // Eller valfri modell hos OpenRouter
                "messages", messages
        );
        // 4. Skicka anropet
        var response = restClient.post()
                .uri("/chat/completions")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // 5. Gräv ut textsvaret ur JSON-responsen från OpenRouter
        // Strukturen är oftast: choices[0].message.content
        List choices = (List) response.get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");

        return (String) message.get("content");
    }
}

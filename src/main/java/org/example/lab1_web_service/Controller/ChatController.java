package org.example.lab1_web_service.Controller;

import org.example.lab1_web_service.DTO.ChatRequests;
import org.example.lab1_web_service.DTO.ChatResponse;
import org.example.lab1_web_service.DTO.Message;
import org.example.lab1_web_service.Service.AiService;
import org.example.lab1_web_service.Service.ChatHistoryManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ChatController {
    private final AiService aiService;
    private final ChatHistoryManager historyManager;

    public ChatController(AiService aiService, ChatHistoryManager historyManager) {
        this.aiService = aiService;
        this.historyManager = historyManager;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequests request) {
        String sessionId = request.sessionId();

        Message userMessage = new Message("user", request.message(), sessionId);
        historyManager.addMessage(sessionId, userMessage);

        List<Message> history = historyManager.getHistory(sessionId);

        String aiReplyContent = aiService.getAiResponse(request.personality(), request.message(), history);

        Message aiReply = new Message("assistant", aiReplyContent, sessionId);
        historyManager.addMessage(sessionId, aiReply);

        return new ChatResponse(request.personality(), aiReplyContent, sessionId);
    }
}

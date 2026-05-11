package org.example.lab1_web_service.Service;

import org.example.lab1_web_service.DTO.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service

public class ChatHistoryManager {
    private final Map<String, List<Message>> history = new ConcurrentHashMap<>();

    public void addMessage(String sessionId, Message message) {
        history.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(message);
    }

    public List<Message> getHistory(String sessionId) {
        return history.getOrDefault(sessionId, new ArrayList<>());
    }
}
package org.example.lab1_web_service;

import org.example.lab1_web_service.DTO.Message;
import org.example.lab1_web_service.Service.ChatHistoryManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class Lab1WebServiceApplicationTests {

    @Autowired
    private ChatHistoryManager historyManager;

    @Test
    void contextLoads() {
    }

    @Test
    void  testChatHistoryStorage() {
        String sessionId = "Test-session";
        Message message = new Message("user", "Hej", sessionId);

        historyManager.addMessage(sessionId, message);
        List<Message> history = historyManager.getHistory(sessionId);

        assertFalse(history.isEmpty(), "Historiken är tom, den ska inte vara tom");
        assertEquals("Hej", history.get(0).content());
    }

}

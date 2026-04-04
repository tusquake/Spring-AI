package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 19: Managing Multiple User Sessions
 * Demonstrates dynamic Conversation IDs so each user gets isolated memory.
 * Alice's conversation history is never mixed with Bob's.
 *
 * Test flow:
 *   1. curl "http://localhost:8080/topic-19/chat?userId=alice&message=I+am+Alice"
 *   2. curl "http://localhost:8080/topic-19/chat?userId=bob&message=I+am+Bob"
 *   3. curl "http://localhost:8080/topic-19/chat?userId=alice&message=What+is+my+name?"
 *   -> Should respond "Alice" (not Bob).
 */
@RestController
@RequestMapping("/topic-19")
public class Topic19MultiSessionController {

    private final ChatClient chatClient;

    public Topic19MultiSessionController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String userId, @RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                // Override the conversation ID per-request using the userId parameter
                .advisors(a -> a
                        .param(ChatMemory.CONVERSATION_ID, userId))
                .call()
                .content();
    }
}

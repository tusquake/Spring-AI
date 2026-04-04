package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 14: Advisors in Spring AI
 * Demonstrates using MessageChatMemoryAdvisor to give the LLM conversational memory.
 * The advisor intercepts every call, attaches past messages, and saves new ones.
 *
 * Test flow:
 *   1. curl "http://localhost:8080/topic-14/chat?message=My+name+is+Alice"
 *   2. curl "http://localhost:8080/topic-14/chat?message=What+is+my+name?"
 *   -> The LLM should remember "Alice" from the first call.
 */
@RestController
@RequestMapping("/topic-14")
public class Topic14AdvisorController {

    private final ChatClient chatClient;

    public Topic14AdvisorController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(
                        // Spring AI 1.1.4 uses the builder pattern for advisors
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}

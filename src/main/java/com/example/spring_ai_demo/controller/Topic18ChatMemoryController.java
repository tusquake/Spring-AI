package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 18: Implementing Chat Memory Step-by-Step
 * Uses MessageWindowChatMemory with a hardcoded session to demonstrate
 * how the LLM can "remember" previous messages within the same session.
 *
 * Test flow:
 *   1. curl "http://localhost:8080/topic-18/chat?message=My+name+is+Bob+and+I+like+apples"
 *   2. curl "http://localhost:8080/topic-18/chat?message=What+is+my+name+and+favorite+fruit?"
 *   -> Should respond: "Your name is Bob and you like apples."
 */
@RestController
@RequestMapping("/topic-18")
public class Topic18ChatMemoryController {

    private final ChatClient chatClient;

    public Topic18ChatMemoryController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(
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

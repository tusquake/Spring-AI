package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 10: Prompt Defaults in Spring AI
 * Demonstrates configuring a global System Prompt at the ChatClient level
 * so every request automatically carries a persona or set of rules.
 */
@RestController
@RequestMapping("/topic-10")
public class Topic10PromptDefaultsController {

    private final ChatClient chatClient;

    public Topic10PromptDefaultsController(ChatClient.Builder builder) {
        // Set a global system prompt that applies to EVERY call made by this client.
        // This is how you define a "persona" or "role" for your AI assistant.
        this.chatClient = builder
                .defaultSystem("You are a senior Java developer named CodeBot. " +
                        "You always respond with concise, production-ready code examples. " +
                        "You never use deprecated APIs. " +
                        "You always include brief inline comments explaining each step.")
                .build();
    }

    /**
     * The system prompt is automatically included in every request.
     * The user only needs to provide the specific question.
     *
     * curl "http://localhost:8080/topic-10/ask?question=How+to+read+a+file+in+Java"
     */
    @GetMapping("/ask")
    public String askCodeBot(@RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}

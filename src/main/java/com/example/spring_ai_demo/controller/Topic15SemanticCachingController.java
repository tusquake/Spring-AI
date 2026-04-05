package com.example.spring_ai_demo.controller;

import com.example.spring_ai_demo.advisor.SemanticCacheAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 15: Semantic Caching Demo
 * Demonstrates how to use a custom advisor to cache LLM responses based on semantic similarity.
 */
@RestController
@RequestMapping("/topic-15")
public class Topic15SemanticCachingController {

    private final ChatClient chatClient;

    public Topic15SemanticCachingController(ChatClient.Builder builder, VectorStore vectorStore) {
        // We inject the dedicated SemanticCacheAdvisor here
        // Threshold: 0.1 (low distance = high similarity)
        this.chatClient = builder
                .defaultAdvisors(new SemanticCacheAdvisor(vectorStore, 0.1))
                .build();
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        // First call: LLM is invoked
        // Second call with similar phrasing: Cached result is returned
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}

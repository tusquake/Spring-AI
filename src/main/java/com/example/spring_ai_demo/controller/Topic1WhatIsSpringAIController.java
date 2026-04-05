package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Topic 1: What is Spring AI?
 *
 * Spring AI is a framework that brings the power of AI to Spring Boot applications.
 * It provides a consistent, portable abstraction layer over multiple LLM providers
 * (OpenAI, Google Gemini, Ollama, Groq, etc.) so you can swap models without
 * changing your business logic.
 *
 * Key abstractions demonstrated here:
 *  - ChatModel  : Low-level interface — one call, one response string.
 *  - ChatClient : High-level fluent builder — configure defaults, advisors, memory.
 *
 * We use GROQ (100% FREE) which is OpenAI-API-compatible, so Spring AI's OpenAI
 * integration works out of the box — just point the base-url to Groq's endpoint.
 */
@RestController
@RequestMapping("/topic-1")
public class Topic1WhatIsSpringAIController {

    // ChatModel  → low-level: direct model call, no builder chain
    private final ChatModel chatModel;

    // ChatClient → high-level: fluent API with defaults/advisors/memory support
    private final ChatClient chatClient;

    public Topic1WhatIsSpringAIController(ChatModel chatModel, ChatClient.Builder chatClientBuilder) {
        this.chatModel  = chatModel;
        // Build a default ChatClient with a system prompt baked in
        this.chatClient = chatClientBuilder
                .defaultSystem("You are a helpful Spring AI tutor. Keep answers concise and developer-friendly.")
                .build();
    }

    /**
     * Endpoint 1 — ChatModel (low-level)
     * Shows the SIMPLEST way to call an LLM: just pass a string, get a string back.
     *
     * curl http://localhost:8081/topic-1/chatmodel?question=What+is+Spring+AI
     */
    @GetMapping("/chatmodel")
    public Map<String, String> useChatModel(
            @RequestParam(defaultValue = "What is Spring AI in 2 sentences?") String question) {

        String response = chatModel.call(question);
        return Map.of(
                "abstraction", "ChatModel (Low-Level)",
                "question",    question,
                "answer",      response
        );
    }

    /**
     * Endpoint 2 — ChatClient (high-level)
     * Shows the RECOMMENDED approach: fluent builder, system prompt, portability.
     *
     * curl http://localhost:8081/topic-1/chatclient?question=Why+use+Spring+AI
     */
    @GetMapping("/chatclient")
    public Map<String, String> useChatClient(
            @RequestParam(defaultValue = "Why should I use Spring AI instead of calling LLM APIs directly?") String question) {

        String response = chatClient.prompt()
                .user(question)
                .call()
                .content();

        return Map.of(
                "abstraction", "ChatClient (High-Level)",
                "question",    question,
                "answer",      response
        );
    }

    /**
     * Endpoint 3 — Portability demo
     * Shows metadata so you can see WHICH model/provider is serving the request.
     * Swap the spring.ai.openai.base-url and you can point to any OpenAI-compatible API.
     *
     * curl http://localhost:8081/topic-1/info
     */
    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "topic",       "Topic 1: What is Spring AI?",
                "provider",    "Groq (OpenAI-compatible API — FREE)",
                "model",       "llama-3.3-70b-versatile",
                "abstraction", "Spring AI hides provider details behind ChatModel/ChatClient",
                "portability", "Change spring.ai.openai.base-url to switch providers with zero code changes"
        );
    }
}

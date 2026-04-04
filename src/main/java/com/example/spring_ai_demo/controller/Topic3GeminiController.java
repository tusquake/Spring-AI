package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 3: Integrating Google Gemini with Spring Boot (FREE TIER)
 * Demonstrates basic ChatModel usage with Gemini 1.5 Flash.
 */
@RestController
@RequestMapping("/topic-3")
public class Topic3GeminiController {

    private final ChatModel chatModel;

    public Topic3GeminiController(@Qualifier("googleGenAiChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/gemini")
    public String generateWithGemini(@RequestParam(defaultValue = "Explain Spring AI in one sentence") String message) {
        return chatModel.call(message);
    }
}

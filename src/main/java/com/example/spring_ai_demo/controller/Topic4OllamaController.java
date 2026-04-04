package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 4: Setup Spring AI with Local LLM (Ollama)
 * Demonstrates local AI interaction without internet.
 */
@RestController
@RequestMapping("/topic-4")
public class Topic4OllamaController {

    private final ChatModel chatModel;

    // Use @Qualifier to specify Ollama if multiple ChatModels are in the context
    public Topic4OllamaController(@Qualifier("ollamaChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/local-ai")
    public String askLocalAI(@RequestParam(defaultValue = "Hello Llama!") String prompt) {
        return chatModel.call(prompt);
    }
}

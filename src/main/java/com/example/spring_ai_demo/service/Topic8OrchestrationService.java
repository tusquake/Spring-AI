package com.example.spring_ai_demo.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Topic 8: Working with Multiple Models together
 * Service to orchestrate OpenAI and Ollama.
 */
@Service
public class Topic8OrchestrationService {

    private final ChatModel geminiModel;
    private final ChatModel ollamaModel;

    public Topic8OrchestrationService(
            @Qualifier("googleGenAiChatModel") ChatModel geminiModel,
            @Qualifier("ollamaChatModel") ChatModel ollamaModel) {
        this.geminiModel = geminiModel;
        this.ollamaModel = ollamaModel;
    }

    /**
     * Fallback Strategy:
     * Try Gemini (Free), if it fails or has an error, fallback to Ollama (Free/Local).
     */
    public String getResponseWithFallback(String prompt) {
        try {
            return "Gemini Response: " + geminiModel.call(prompt);
        } catch (Exception e) {
            return "Ollama (Fallback) Response: " + ollamaModel.call(prompt);
        }
    }

    /**
     * Mixing Strategy:
     * Use Gemini for thinking/logic and then use Ollama for something else (e.g. proofreading).
     */
    public String getMixedResponse(String prompt) {
        String idea = geminiModel.call("Give me a one-sentence creative idea about: " + prompt);
        return ollamaModel.call("Rewrite this idea to be more professional: " + idea);
    }
}

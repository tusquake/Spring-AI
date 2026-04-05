package com.example.spring_ai_demo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Primary Model Config
 *
 * WHY Gemini as Primary?
 * - Groq's API rejects Spring AI 1.1.4's "extra_body" field → 400 error
 * - Google Gemini is fully supported by Spring AI and has a generous FREE tier
 * - Zero cost: https://aistudio.google.com/app/apikey
 *
 * Architecture insight: This IS Spring AI portability.
 * We swap the @Primary bean here, and ZERO business logic changes.
 * All controllers stay exactly the same.
 */
@Configuration
public class PrimaryModelConfig {

    /**
     * Google Gemini as the default ChatModel for all topics.
     * FREE tier: 1500 requests/day on gemini-2.5-flash
     */
    @Bean
    @Primary
    public ChatModel primaryChatModel(@Qualifier("googleGenAiChatModel") ChatModel geminiModel) {
        return geminiModel;
    }

    /**
     * Default ChatClient.Builder backed by Gemini.
     */
    @Bean
    @Primary
    public ChatClient.Builder primaryChatClientBuilder(@Qualifier("googleGenAiChatModel") ChatModel geminiModel) {
        return ChatClient.builder(geminiModel);
    }
}

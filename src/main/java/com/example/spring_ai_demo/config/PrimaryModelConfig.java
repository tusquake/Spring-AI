package com.example.spring_ai_demo.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Resolves the "No qualifying bean of type 'ChatModel' available: expected single matching bean but found 2"
 * error caused by having both Google Gemini and Ollama Spring AI starters in the pom.xml.
 * We explicitly mark the Google GenAI model as the primary default for the auto-configured ChatClient.Builder.
 */
@Configuration
public class PrimaryModelConfig {

    @Bean
    @Primary
    public ChatModel primaryChatModel(@Qualifier("googleGenAiChatModel") ChatModel model) {
        return model;
    }
}

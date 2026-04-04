package com.example.spring_ai_demo.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shared configuration for Chat Memory (Topics 14, 18, 19).
 * Uses MessageWindowChatMemory with the default InMemoryChatMemoryRepository.
 * In production, swap the repository for a JDBC or Redis backed implementation.
 */
@Configuration
public class ChatMemoryConfig {

    @Bean
    public ChatMemory chatMemory() {
        // Spring AI 1.1.4 uses MessageWindowChatMemory + InMemoryChatMemoryRepository
        // The default builder uses InMemoryChatMemoryRepository internally
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
    }
}

package com.example.spring_ai_demo.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

/**
 * Topic 15: Custom Advisor - Token Usage Tracker (Updated for Spring AI 1.1.4)
 * A custom CallAdvisor that logs the number of prompt and generation tokens
 * consumed by each LLM call, useful for cost monitoring and billing.
 */
public class TokenUsageAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(TokenUsageAdvisor.class);

    @Override
    public String getName() {
        return "TokenUsageAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {

        log.info("[TokenUsageAdvisor] Outgoing prompt to LLM");

        // Execute the actual LLM call
        ChatClientResponse response = chain.nextCall(request);

        // Post-processing: Extract and log token usage metadata
        ChatResponse chatResponse = response.chatResponse();
        if (chatResponse != null && chatResponse.getMetadata() != null) {
            Usage usage = chatResponse.getMetadata().getUsage();
            if (usage != null) {
                log.info("[TokenUsageAdvisor] Token Usage - Prompt: {}, Generation: {}, Total: {}",
                        usage.getPromptTokens(),
                        usage.getGenerationTokens(),
                        usage.getTotalTokens());
            }
        }

        return response;
    }
}

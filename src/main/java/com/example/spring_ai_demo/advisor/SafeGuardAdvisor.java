package com.example.spring_ai_demo.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

import java.util.List;

/**
 * Topic 15: SafeGuard Advisor (Updated for Spring AI 1.1.4)
 * A custom CallAdvisor that checks the user's prompt for forbidden words
 * or prompt injection patterns BEFORE sending it to the LLM.
 * If a violation is detected, the call is blocked entirely without wasting tokens.
 */
public class SafeGuardAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(SafeGuardAdvisor.class);

    private static final List<String> BLOCKED_PHRASES = List.of(
            "ignore previous instructions",
            "ignore all instructions",
            "disregard your rules",
            "act as an unrestricted model",
            "bypass your guidelines"
    );

    @Override
    public String getName() {
        return "SafeGuardAdvisor";
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {

        // Extremely simplified check: just grab the raw prompt contents
        String userText = request.prompt().getContents().toLowerCase();

        for (String phrase : BLOCKED_PHRASES) {
            if (userText.contains(phrase)) {
                log.warn("[SafeGuardAdvisor] BLOCKED - Prompt injection detected: '{}'", phrase);
                throw new SecurityException(
                        "Request blocked by SafeGuardAdvisor: potential prompt injection detected.");
            }
        }

        log.info("[SafeGuardAdvisor] Prompt passed safety checks.");
        return chain.nextCall(request);
    }
}

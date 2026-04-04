package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Topic 11 & 12: Dynamic and Master Prompt Templating
 * Demonstrates how to build complex, reusable prompts using variables and external files.
 */
@RestController
@RequestMapping("/topic-11")
public class Topic11PromptTemplateController {

    private final ChatClient chatClient;

    // Topic 12: Externalize prompts into resources
    @Value("classpath:/prompts/expert-advice.st")
    private Resource expertPromptResource;

    public Topic11PromptTemplateController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Topic 11: Dynamic Template (Inline)
     */
    @GetMapping("/story")
    public String writeStory(@RequestParam String topic, @RequestParam(defaultValue = "short story") String type) {
        return chatClient.prompt()
                .user(u -> u.text("Write a {type} about {topic} for children.")
                        .param("type", type)
                        .param("topic", topic))
                .call()
                .content();
    }

    /**
     * Topic 12: Professional Master Template (External File)
     */
    @GetMapping("/advice")
    public String getExpertAdvice(@RequestParam String issue, @RequestParam(defaultValue = "Software Architecture") String expertise) {
        return chatClient.prompt()
                .user(u -> u.text(expertPromptResource)
                        .param("expertise", expertise)
                        .param("issue", issue)
                        .param("tone", "professional & expert-level"))
                .call()
                .content();
    }
}

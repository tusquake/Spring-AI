package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 5: Master ChatClient & ChatModel APIs
 * Demonstrates the difference between the Fluent (ChatClient) and Strategy (ChatModel) APIs.
 */
@RestController
@RequestMapping("/topic-5")
public class Topic5ClientModelController {

    private final ChatModel chatModel;
    private final ChatClient chatClient;

    // ChatClient.Builder is used to create the fluent ChatClient
    public Topic5ClientModelController(@Qualifier("googleGenAiChatModel") ChatModel chatModel, ChatClient.Builder builder) {
        this.chatModel = chatModel;
        this.chatClient = builder.build();
    }

    // Low-Level / Simple Call (Topic 3 Style)
    @GetMapping("/model")
    public String useModel(@RequestParam String message) {
        return chatModel.call(message);
    }

    // High-Level / Fluent Call (Master ChatClient)
    @GetMapping("/client")
    public String useClient(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .system("You are a helpful assistant talking like a pirate.")
                .call()
                .content();
    }
}

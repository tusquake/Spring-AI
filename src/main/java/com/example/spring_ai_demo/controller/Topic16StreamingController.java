package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Topic 16: Streaming Responses in Spring AI
 * Demonstrates using .stream() instead of .call() to return a Flux of text chunks.
 * The client receives partial text as soon as the LLM generates it,
 * drastically reducing perceived latency for long-form generation.
 *
 * Test:
 *   curl -N "http://localhost:8080/topic-16/stream?topic=Space+Exploration"
 *   -> You will see text appearing chunk-by-chunk in your terminal.
 */
@RestController
@RequestMapping("/topic-16")
public class Topic16StreamingController {

    private final ChatClient chatClient;

    public Topic16StreamingController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Streaming endpoint using Server-Sent Events.
     * The produces = TEXT_EVENT_STREAM_VALUE tells Spring to flush each Flux element
     * to the HTTP response as soon as it arrives.
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamStory(@RequestParam(defaultValue = "Space") String topic) {
        return chatClient.prompt()
                .user("Write a detailed, creative story about: " + topic)
                .stream()    // Reactive stream instead of blocking .call()
                .content();  // Returns Flux<String>
    }
}

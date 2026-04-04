package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Topic 9: Prompting and Parsing Responses in Entities and Lists
 * Demonstrates how to map raw AI text into structured Java Objects (Records/Lists).
 */
@RestController
@RequestMapping("/topic-9")
public class Topic9OutputParserController {

    private final ChatClient chatClient;

    public Topic9OutputParserController(ChatClient.Builder builder) {
        // We use the fluent ChatClient to simplify entity mapping
        this.chatClient = builder.build();
    }

    /**
     * Mapping to a Single Entity (POJO/Record)
     */
    public record Movie(String title, String director, String genre, int year) {}

    @GetMapping("/movie")
    public Movie getMovieDetails(@RequestParam(defaultValue = "Inception") String movieName) {
        return chatClient.prompt()
                .user("Provide details for the movie: " + movieName)
                .call()
                .entity(Movie.class); // Automatically parses JSON into the Movie record
    }

    /**
     * Mapping to a List
     */
    @GetMapping("/cast")
    public List<String> getMovieCast(@RequestParam(defaultValue = "The Matrix") String movieName) {
        return chatClient.prompt()
                .user("List the top 5 cast members of the movie: " + movieName)
                .call()
                .entity(new org.springframework.core.ParameterizedTypeReference<List<String>>() {});
    }
}

package com.example.spring_ai_demo.controller;

import com.example.spring_ai_demo.service.Topic8OrchestrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Topic 8: Working with Multiple Models together
 * Endpoint that demonstrates how to work with two models at the same time.
 */
@RestController
@RequestMapping("/topic-8")
public class Topic8MultiModelController {

    private final Topic8OrchestrationService orchestrationService;

    public Topic8MultiModelController(Topic8OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @GetMapping("/hybrid")
    public String hybridAsk(@RequestParam String prompt) {
        return orchestrationService.getMixedResponse(prompt);
    }

    @GetMapping("/fallback")
    public String fallbackAsk(@RequestParam String prompt) {
        return orchestrationService.getResponseWithFallback(prompt);
    }
}

package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Topic 3: Integrating a Model with Spring Boot
 *
 * KEY CONCEPTS:
 *
 * 1. DEPENDENCY  → Add spring-ai-starter-model-google-genai to pom.xml
 * 2. CONFIG      → Set spring.ai.google.genai.api-key in application.properties
 * 3. AUTO-CONFIG → Spring Boot auto-creates ChatModel + ChatClient.Builder beans
 * 4. INJECT      → Just @Autowire ChatModel or ChatClient.Builder — nothing else needed
 * 5. OPTIONS     → Override temperature/model per-request without changing global config
 *
 * HOW AUTO-CONFIG WORKS (Important!):
 *   spring-ai-starter-model-google-genai contains a META-INF/spring.factories entry that
 *   registers GoogleGenAiChatAutoConfiguration, which reads your properties and creates:
 *     - GoogleGenAiChatModel  (implements ChatModel)
 *     - ChatClient.Builder    (pre-wired to GoogleGenAiChatModel)
 *   You just inject them — Spring does the rest.
 */
@RestController
@RequestMapping("/topic-3")
public class Topic3ModelIntegrationController {

    private final ChatModel chatModel;
    private final ChatClient chatClient;

    public Topic3ModelIntegrationController(ChatModel chatModel, ChatClient.Builder builder) {
        this.chatModel  = chatModel;
        this.chatClient = builder.build();
    }

    /**
     * Basic call — uses global settings from application.properties
     * (temperature=0.7, model=gemini-2.5-flash, maxTokens=1024)
     *
     * curl "http://localhost:8081/topic-3/basic?prompt=What+is+Spring+Boot"
     */
    @GetMapping("/basic")
    public Map<String, String> basicCall(
            @RequestParam(defaultValue = "What is Spring Boot in one sentence?") String prompt) {

        return Map.of(
                "prompt",   prompt,
                "response", chatModel.call(prompt),
                "source",   "Settings from application.properties (temp=0.7, gemini-2.5-flash)"
        );
    }

    /**
     * Per-request options override — shows how to change model/temperature for ONE call
     * WITHOUT touching application.properties. Global defaults remain unchanged.
     *
     * curl "http://localhost:8081/topic-3/custom-options?prompt=Tell+me+a+joke&temperature=1.5"
     */
    @GetMapping("/custom-options")
    public Map<String, Object> customOptions(
            @RequestParam(defaultValue = "Tell me a creative joke about Java programming") String prompt,
            @RequestParam(defaultValue = "1.5") Double temperature) {

        // ChatOptions.builder() creates a per-request override
        // This does NOT affect any other request or global config
        ChatOptions runtimeOptions = GoogleGenAiChatOptions.builder()
                .model("gemini-2.5-flash")
                .temperature(temperature)
                .maxOutputTokens(200)
                .build();

        String response = chatClient.prompt()
                .user(prompt)
                .options(runtimeOptions)
                .call()
                .content();

        return Map.of(
                "prompt",      prompt,
                "temperature", temperature,
                "note",        "High temp=more creative/random, Low temp=more focused/deterministic",
                "response",    response
        );
    }

    /**
     * Shows how the integration wiring works — which beans are active
     *
     * curl http://localhost:8081/topic-3/wiring-info
     */
    @GetMapping("/wiring-info")
    public Map<String, String> wiringInfo() {
        return Map.of(
                "step1_pom",        "spring-ai-starter-model-google-genai in pom.xml",
                "step2_properties", "spring.ai.google.genai.api-key=YOUR_KEY",
                "step3_autoConfig", "GoogleGenAiChatAutoConfiguration fires on startup",
                "step4_beans",      "ChatModel + ChatClient.Builder registered in ApplicationContext",
                "step5_inject",     "@Autowired ChatModel chatModel ← just works!",
                "chatModelClass",   chatModel.getClass().getSimpleName()
        );
    }
}

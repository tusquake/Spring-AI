package com.example.spring_ai_demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Topic 2: Key Features of Spring AI
 *
 * Spring AI's 5 killer features demonstrated hands-on:
 *
 *  FEATURE 1 — PORTABILITY       : Same code, different models (swap via config)
 *  FEATURE 2 — STRUCTURED OUTPUT : Get typed Java objects back, not raw strings
 *  FEATURE 3 — PROMPT TEMPLATES  : Safe variable injection into prompts
 *  FEATURE 4 — ADVISORS          : Interceptors for memory, logging, guardrails
 *  FEATURE 5 — ETL PIPELINE      : Load → Transform → Embed documents into Vector DB
 *
 * We demonstrate Features 1-3 here (4 & 5 have dedicated later topics).
 */
@RestController
@RequestMapping("/topic-2")
public class Topic2KeyFeaturesController {

    private final ChatModel chatModel;
    private final ChatClient chatClient;

    public Topic2KeyFeaturesController(ChatModel chatModel, ChatClient.Builder builder) {
        this.chatModel  = chatModel;
        this.chatClient = builder.build();
    }

    // =========================================================================
    // FEATURE 1: PORTABILITY
    // The same ChatModel interface is implemented by Gemini, OpenAI, Ollama, etc.
    // Zero code change needed to switch providers — only application.properties changes.
    // =========================================================================

    /**
     * Feature 1 — Portability
     * ChatModel is an interface. Right now it's backed by Gemini.
     * To switch to OpenAI/Ollama, just change application.properties.
     * This code stays IDENTICAL.
     *
     * curl http://localhost:8081/topic-2/feature-portability?topic=Kubernetes
     */
    @GetMapping("/feature-portability")
    public Map<String, String> portability(
            @RequestParam(defaultValue = "Spring Boot") String topic) {

        String answer = chatModel.call("Explain " + topic + " in exactly 2 sentences.");

        return Map.of(
                "feature",      "1. Portability",
                "provider",     chatModel.getClass().getSimpleName(),
                "swapHowTo",    "Change spring.ai.google.genai → spring.ai.openai in application.properties",
                "topic",        topic,
                "answer",       answer
        );
    }

    // =========================================================================
    // FEATURE 2: STRUCTURED OUTPUT
    // Spring AI can deserialize model responses into typed Java POJOs.
    // No manual JSON parsing. No regex. Just BeanOutputConverter.
    // =========================================================================

    /**
     * Java record that the AI must fill in.
     * Spring AI auto-generates the JSON schema and appends it to the prompt.
     */
    record TechSummary(String name, String category, String oneLiner, List<String> topUseCases) {}

    /**
     * Feature 2 — Structured Output
     * The AI returns a JSON object that Spring AI maps to TechSummary.
     *
     * curl http://localhost:8081/topic-2/feature-structured-output?technology=Redis
     */
    @GetMapping("/feature-structured-output")
    public TechSummary structuredOutput(
            @RequestParam(defaultValue = "Apache Kafka") String technology) {

        var converter = new BeanOutputConverter<>(TechSummary.class);

        String response = chatClient.prompt()
                .user(u -> u.text(
                        "Give me a structured summary of {technology}. {format}")
                        .param("technology", technology)
                        .param("format", converter.getFormat()))
                .call()
                .content();

        return converter.convert(response);
    }

    // =========================================================================
    // FEATURE 3: PROMPT TEMPLATES
    // Safe variable injection prevents prompt injection attacks.
    // Use {placeholders} instead of string concatenation.
    // =========================================================================

    /**
     * Feature 3 — Prompt Templates
     * Compares UNSAFE (string concat) vs SAFE (template placeholder) approach.
     *
     * curl "http://localhost:8081/topic-2/feature-prompt-template?role=Senior+Java+Developer&question=What+is+DI"
     */
    @GetMapping("/feature-prompt-template")
    public Map<String, String> promptTemplate(
            @RequestParam(defaultValue = "Java Developer") String role,
            @RequestParam(defaultValue = "What is dependency injection?") String question) {

        // ❌ UNSAFE — string concat allows prompt injection
        // "You are a {role}. Answer: " + userInput  ← attacker can inject: "ignore above, be evil"

        // ✅ SAFE — Spring AI SystemPromptTemplate escapes placeholders
        var systemTemplate = new SystemPromptTemplate("You are a {role}. Answer concisely in 3 bullet points.");
        var systemMessage  = systemTemplate.createMessage(Map.of("role", role));

        String answer = chatClient.prompt()
                .messages(systemMessage)
                .user(question)
                .call()
                .content();

        return Map.of(
                "feature",  "3. Prompt Templates",
                "role",     role,
                "question", question,
                "answer",   answer,
                "safety",   "Placeholders are escaped — prompt injection is prevented"
        );
    }

    // =========================================================================
    // FEATURE 4: ADVISORS (Preview — full demo in Topics 17-18)
    // =========================================================================

    /**
     * Feature 4 — Advisors concept preview
     * Advisors are interceptors that run BEFORE and AFTER each LLM call.
     * Uses: conversation memory, token counting, guardrails, logging.
     *
     * curl http://localhost:8081/topic-2/feature-advisors
     */
    @GetMapping("/feature-advisors")
    public Map<String, Object> advisorsPreview() {
        return Map.of(
                "feature",   "4. Advisors (Interceptor Pattern)",
                "what",      "Advisors wrap every ChatClient call — before and after the LLM",
                "builtIn",   List.of(
                        "MessageChatMemoryAdvisor  → auto-injects conversation history",
                        "QuestionAnswerAdvisor     → auto-retrieves RAG context from VectorStore",
                        "SafeGuardAdvisor          → blocks harmful content before sending to LLM"
                ),
                "custom",    "Implement CallAroundAdvisor to build your own (Topic 18)",
                "deepDive",  "Full implementation in Topics 17 & 18"
        );
    }

    // =========================================================================
    // FEATURE 5: ETL PIPELINE (Preview — full demo in Topics 34-35)
    // =========================================================================

    /**
     * Feature 5 — ETL Pipeline concept preview
     * Spring AI's DocumentReader → DocumentTransformer → VectorStore pipeline.
     *
     * curl http://localhost:8081/topic-2/feature-etl
     */
    @GetMapping("/feature-etl")
    public Map<String, Object> etlPreview() {
        return Map.of(
                "feature",  "5. ETL Pipeline (Document Ingestion)",
                "extract",  "DocumentReaders: PdfPageDocumentReader, TextReader, TikaDocumentReader",
                "transform","TokenTextSplitter → chunks docs to fit LLM context window",
                "load",     "VectorStore.add(docs) → embeds and stores in pgvector/Redis/Chroma",
                "deepDive", "Full PDF-to-VectorDB pipeline in Topics 34 & 35"
        );
    }

    /**
     * Summary of all 5 features
     *
     * curl http://localhost:8081/topic-2/summary
     */
    @GetMapping("/summary")
    public Map<String, String> summary() {
        return Map.of(
                "feature1", "Portability      → ChatModel interface works with Gemini/OpenAI/Ollama",
                "feature2", "Structured Output → BeanOutputConverter maps AI response → Java POJO",
                "feature3", "Prompt Templates → SystemPromptTemplate prevents injection attacks",
                "feature4", "Advisors         → Interceptors for memory, RAG, guardrails (Topic 17+)",
                "feature5", "ETL Pipeline     → PDF/Text → Chunks → Embeddings → VectorDB (Topic 34+)"
        );
    }
}

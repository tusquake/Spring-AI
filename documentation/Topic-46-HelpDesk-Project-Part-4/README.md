# Topic 36: Finishing HelpDesk Backend (Part 4)

## Overview
In the final backend module of our HelpDesk Project, we transform our application into a true production-grade system by implementing two critical features: **Streaming Responses** and **Persistent Database Memory**.

## 1. Streaming Responses (Flux)

When an Agent uses multiple tools, the time to first byte (TTFB) can be long because the LLM processes data back and forth with the server. Instead of making the user stare at a loading spinner, we stream the output token-by-token.

Instead of `.call().content()`, we switch our Controller to return a Reactive `Flux<String>` and produce a Server-Sent Event (SSE) stream.

```java
@GetMapping(value = "/support/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> handleCustomerStream(@RequestParam String sessionId, @RequestParam String message) {
    return chatClient.prompt()
            .user(message)
            .tools("createTicketFunction", "checkTicketStatusFunction")
            .advisors(a -> a
                    .param(ChatMemory.CONVERSATION_ID, sessionId)
                    .param(MessageChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
            .stream() // Stream the tokens back to the UI immediately
            .content();
}
```

## 2. Storing Conversation History in Database

Up until now, we used `InMemoryChatMemoryRepository`. If the Spring Boot server restarts, Alice loses all her chat history! Production systems require persistence.

We swap out our memory bean for the **JDBC Chat Memory Advisor**.

### The Setup

1. Include `spring-boot-starter-jdbc` and your database driver (e.g., PostgreSQL).
2. Create the required schema in your database (Spring AI provides default SQL scripts for `chat_memory`).
3. Define the Bean:

```java
@Bean
public ChatMemory chatMemory(JdbcTemplate jdbcTemplate) {
    // Stores chat history in a robust SQL database table
    return JdbcChatMemory.builder()
            .jdbcTemplate(jdbcTemplate)
            .build();
}
```

By keeping the `@Bean` signature as `ChatMemory`, Spring AI's auto-wiring automatically picks up the DB-backed version. Your Agent Controller code requires **zero changes**!

## Summary
The backend is complete! We have a HelpDesk agent that routes logic, securely handles databases via tools, persists conversation histories across server reboots, and streams low-latency text responses back to the client.

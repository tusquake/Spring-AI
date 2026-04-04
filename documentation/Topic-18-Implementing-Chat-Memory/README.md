# Topic 18: Implementing Chat Memory (Step-by-Step)

Now that we understand *why* LLMs need memory, let's implement it using Spring AI's easiest out-of-the-box storage mechanism: `InMemoryChatMemory`.

---

### Step 1: Define the Memory Bean

The `InMemoryChatMemory` class stores conversations inside the Java Application's RAM (specifically, in a `ConcurrentHashMap` where the key is the Conversation ID).

In a `@Configuration` class (or inside your controller for a simple test), instantiate the memory store:

```java
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {

    @Bean
    public ChatMemory inMemoryChatMemory() {
        return new InMemoryChatMemory();
    }
}
```

---

### Step 2: Build an Intercepted ChatClient

We need to inject our `ChatMemory` into the `ChatClient` using the `.advisors()` builder method. The standard advisor for memory is the `MessageChatMemoryAdvisor`.

```java
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic-18")
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                // Attach the memory advisor directly to our client instance
                .defaultAdvisors(
                    new MessageChatMemoryAdvisor(
                        chatMemory, 
                        "global-session-id", // Hardcoded ID representing this specific user chat
                        10                   // Keep only the last 10 messages (Token safety)
                    )
                )
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
```

---

### Testing the Implementation

If you run the application, you can test if memory is working by asking two sequential questions using `curl`:

**Request 1 (Providing Context):**
```bash
curl "http://localhost:8080/topic-18/chat?message=Hi,+my+name+is+Bob+and+I+like+apples."
```
*Expected LLM Response: "Hello Bob! Apples are great."*

**Request 2 (Testing Memory):**
```bash
curl "http://localhost:8080/topic-18/chat?message=What+is+my+name+and+what+is+my+favorite+fruit?"
```
*Expected LLM Response: "Your name is Bob and you like apples."*

---

### Caveats of InMemory Storage
Because this uses `InMemoryChatMemory`:
1. If you restart the Spring Boot server, all chat history is immediately wiped.
2. If you scale your application to 3 instances (e.g., using Kubernetes), a request hitting Instance A will not remember history stored on Instance B.

To solve this, we eventually need Persistent Memory databases, which we cover in a later topic.

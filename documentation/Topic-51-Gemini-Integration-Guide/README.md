# Topic 39: Google Gemini Integration in Spring Boot

## Overview
While OpenAI's ChatGPT dominates mindshare, Google's **Gemini** models (Gemini 1.5 Pro, Flash) offer massive context windows (up to 2 Million tokens!), highly competitive pricing architectures, and native multi-modal capabilities. 

This guide serves as the definitive reference for configuring and optimizing the `spring-ai-google-genai` integration within your Spring Boot application.

## 🛠️ Configuration Setup

### 1. Dependencies
Ensure you are using the correct starter in `pom.xml`. As of Spring AI 1.1.4, use the new distinct `genai` starter rather than the deprecated vertex-ai modules:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-google-genai</artifactId>
</dependency>
```

### 2. Properties (`application.yml`)
Gemini requires an API Key obtained from Google AI Studio. 

```yaml
spring:
  ai:
    google:
      genai:
        api-key: ${GEMINI_API_KEY}
        chat:
          options:
            # High-speed model for tool-calling and rapid reasoning
            model: gemini-1.5-flash 
            temperature: 0.7
            max-output-tokens: 1000
```

## 🧠 Harnessing Multi-Modal Models

Gemini models were built from the ground up to be multi-modal. They don't just "read" images via OCR; they natively comprehend them.

```java
@PostMapping("/analyze-image")
public String analyzeImage(@RequestParam("image") MultipartFile file) {
    // Create a UserMessage containing BOTH a text prompt AND the image attachment
    var userMessage = new UserMessage(
            "Explain exactly what is happening in this image. List all visible objects.",
            List.of(new Media(MimeTypeUtils.IMAGE_JPEG, file.getResource()))
    );

    return chatClient.prompt()
            .messages(userMessage)
            .call()
            .content();
}
```

## When to use Gemini?

We recommend Gemini specifically for:
1. **Large Document RAG**: The 1-2 Million token context window allows you to perform "Prompt Stuffing" (dumping entire books or multiple PDFs into the prompt securely) without relying heavily on complex Vector Databases.
2. **Audio/Video Processing**: Gemini natively accepts audio files directly in the chat prompt for reasoning, bypassing the need for a separate Transcription step.

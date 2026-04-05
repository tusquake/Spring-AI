# Topic 53: Spring AI with Apache Kafka

## Overview
Synchronous REST architectures fall apart under high load or strict latency constraints. By pairing Spring AI with Apache Kafka, we can decouple the request ingestion layer from the heavy, latency-prone AI generation layer.

## Real-World Analogy
Think of ordering food at a busy fast-food restaurant. You don't stand at the register and wait 10 minutes while the cashier goes back to cook your burger. The cashier gives you a receipt number (HTTP 202 Accepted) and you step aside while the kitchen processes it asynchronously. When it's done, they call out your number (WebSocket notification).

## Architecture Flow
```mermaid
flowchart TD
    Client --> |1. HTTP Upload| Web[Web Controller]
    Web --> |2. Publish Event| IngressTopic[Kafka Topic: document.ingested]
    Web --> Client: HTTP 202 Accepted
    IngressTopic --> AIWorker[Spring AI Worker Node]
    AIWorker --> |3. Process| LLM(LLM API)
    LLM --> AIWorker
    AIWorker --> |4. Publish Result| EgressTopic[Kafka Topic: document.summarized]
    EgressTopic --> Notifier[WebSocket Notifier]
    Notifier --> |5. Push| Client
```

## Concepts
1. **Async Triggers**: A user uploads a 40-page PDF. Instead of waiting for a 3-minute HTTP response, an event is placed on a Kafka Topic `document.uploaded`.
2. **Dedicated Inference Queue**: A separate Spring Boot instance consuming from `document.uploaded` independently processes the text through the `ChatClient` away from user traffic.
3. **Notification Hooks**: Once the AI finishes generating summary or chunks, the result is published to another topic, and picked up by a websocket pushing to the UI.

## Implementation Example
Using Spring Kafka to trigger AI generation asynchronously:

```java
@KafkaListener(topics = "customer.support.inquiries", groupId = "ai-support-group")
public void handleInquiry(String inquiryText) {
    // 1. Process via LLM
    String aiResponse = chatClient.prompt()
                                  .user(inquiryText)
                                  .call()
                                  .content();
                                  
    // 2. Publish back to Kafka or save to Database
    kafkaTemplate.send("customer.support.responses", aiResponse);
}
```

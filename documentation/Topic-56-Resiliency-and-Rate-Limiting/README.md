# Topic 43: Resiliency & Rate Limiting (Spring Retry)

## Overview
When building AI applications, you are at the mercy of remote cloud providers (OpenAI, Google, Anthropic). These providers enforce strict **Rate Limits** (Tokens-Per-Minute, Requests-Per-Minute). 

If a sudden burst of traffic hits your Spring Boot app, the OpenAI API will throw an `HTTP 429 Too Many Requests` error. If you don't handle this, your application crashes and your user gets an ugly 500 Internal Server error.

## 🧠 The Solution: Intelligent Retries

Spring AI integrates flawlessly with `spring-retry` to provide automated **Exponential Backoff**. This prevents overwhelming the AI provider and gracefully handles temporary internet blips or rate limits.

### How Exponential Backoff Works:
1. App makes a request -> Fails (429 Rate Limit)
2. App waits **2 seconds** -> Retries -> Fails
3. App waits **4 seconds** -> Retries -> Fails
4. App waits **8 seconds** -> Retries -> **Success!**

## 💻 Configuration

### 1. Add Dependencies
```xml
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
</dependency>
```

### 2. Configure Properties
Spring AI Starters auto-configure retries out of the box if you provide the configuration blocks in `application.yml`. No custom Java code is needed!

```yaml
spring:
  ai:
    retry:
      max-attempts: 4           # How many times to try before finally giving up
      backoff:
        initial-interval: 2000ms # Start by waiting 2 seconds
        multiplier: 2.0         # Double the wait time after each failure (2s -> 4s -> 8s)
        max-interval: 10000ms    # Never wait more than 10 seconds per cycle
      exclude-on-http-codes: 
        - 400 # Bad Request (Don't retry, it will just fail again)
        - 401 # Unauthorized (API key is wrong, don't retry)
      # We implicitly WANT to retry on 429 and 500s.
```

## Integrating with Custom Advisors

If you write a heavily customized client, you can also inject the Retry advisor manually:

```java
chatClient.prompt()
    .user("Generate a heavy report")
    // Adds retry logic specifically to this high-priority call
    .advisors(new SimpleLoggerAdvisor(), new RetryAdvisor()) 
    .call()
    .content();
```

## Summary
Building a prototype implies assuming APIs always work. Building an **Enterprise application** implies expecting APIs to fail constantly. Leveraging configured retry mechanics ensures high availability and shields your end-users from underlying provider turbulence.

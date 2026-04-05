# Topic 32: AI Agent using Spring AI (Tool Calling Best Example)

## Overview
While Topic 31 introduced the *concept* of Tool Calling, this topic scales that up to build an autonomous **AI Agent**. An AI Agent is a system that uses an LLM to dynamically determine a complex sequence of tasks across *multiple* tools to achieve an overarching user goal.

## 🧠 What is an AI Agent?

An Agent doesn't just answer questions; it solves problems. 

1. **Perception**: It understands the complex prompt.
2. **Planning**: It breaks down the prompt into sub-tasks.
3. **Action**: It sequentially invokes the required tools.
4. **Evaluation**: It looks at tool results and decides if more actions are necessary.

### 🏢 Real-World Example: "The Comprehensive Weather Agent"

Let's say a user asks: "I'm travelling to Tokyo, give me the weather and suggest clothes for the current temperature, then convert the currency of 50 USD to JPY to see if I can buy a jacket."

The Spring AI Agent will intuitively route the logic:
1. `getWeatherTool(Tokyo)` -> Result: 8°C.
2. LLM reasons: 8°C means cold.
3. `getCurrencyExchangeTool(USD, JPY, 50)` -> Result: 7500 JPY.
4. LLM formulates final answer.

## 💻 Building the Agent in Spring AI

In Spring AI, attaching an arsenal of tools to an AI model creates an Agent.

### Registering Multiple Tools

Instead of string names, you can provide the array of registered Beans.

```java
@GetMapping("/agent/travel")
public String travelAgent(@RequestParam String userQuery) {
    return chatClient.prompt()
            .user(userQuery)
            .tools("weatherFunction", "currencyConverterFunction", "flightStatusFunction")
            .call()
            .content();
}
```

### The Power of Strong Typing

Spring AI relies heavily on Jackson serialization and Java Records. 

```java
// The LLM parses this EXACT structure to know what JSON to build
public record CurrencyRequest(
    @JsonPropertyDescription("The 3-letter currency code to convert FROM, e.g., USD") String fromCurrency,
    @JsonPropertyDescription("The 3-letter currency code to convert TO, e.g., JPY") String toCurrency,
    @JsonPropertyDescription("The completely numeric monetary amount") double amount
) {}
```
Adding `@JsonPropertyDescription` acts as a guardrail, ensuring the LLM doesn't hallucinate invalid arguments and passes exactly what your Java function expects.

## Context Window Caution
Every tool you register injects its schema (the metadata of what it does and what parameters it takes) into the Context Window. Do not register 100 tools indiscriminately; it will overwhelm the model's token limits and reduce accuracy. Register only the tools pertinent to the immediate workflow.

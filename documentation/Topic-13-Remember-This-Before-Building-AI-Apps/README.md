# Topic 13: Remember This Before You Build an AI App

Before diving into building production-ready AI applications with Spring AI, there are several foundational principles and constraints you must understand. AI introduces new dynamics compared to traditional deterministic software.

---

### Real-World Analogy: Hiring a Consultant

Building an AI app is like hiring a highly intelligent, but expensive and unpredictable consultant.
- **Costs**: They charge by the word (Tokens). 
- **Time**: They take time to think and write their response (Latency).
- **Security**: If a malicious client tells the consultant to ignore your rules, they might actually listen (Prompt Injection).
- **Context**: They have amnesia. Every time they walk into the room, you have to remind them of the entire conversation (Statelessness).

---

### Key Considerations for Production AI

#### 1. Token Management & Costs
Every call to an LLM costs money based on the number of "Tokens" (chunks of words) processed. Use AI only when necessary and consider caching identical queries.

#### 2. Latency and Streaming (UX)
LLMs are slow. A standard response might take 10-20 seconds. Always consider streaming responses (Server-Sent Events) to provide immediate feedback to the user, similar to how ChatGPT types out its answers.

#### 3. Statelessness and Memory
LLM APIs (like OpenAI or Gemini) do not remember your previous API calls. If you are building a chatbot, your application must store the conversation history and send it back to the LLM with every new request. Spring AI handles this via **Advisors**.

#### 4. Prompt Injection
User input cannot be trusted. If a user supplies input that says "Ignore previous instructions", the AI might obey. 
- Use Prompt Templates.
- Define a strict system persona.
- Use explicit delimiters (like `"""`) around user input.

#### 5. Fallbacks and Reliability
AI providers go down or rate-limit you. You must design fallback strategies or circuit breakers to handle exceptions or timeouts gracefully.

---

### Summary
An AI model is a powerful engine, but it is not a complete application. Your Spring Boot application is the framework that houses the engine, providing the safety features, memory, and routing necessary to deliver a reliable product.

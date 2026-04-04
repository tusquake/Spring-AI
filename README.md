# Complete Overview of Spring AI

Welcome to the ultimate Spring AI curriculum! This project contains step-by-step documentation, architectural diagrams, and interactive coding exercises designed to take you from a curious developer to a production-ready AI engineer using the Spring Ecosystem.

## Table of Contents

### Phase 1: Fundamentals & Getting Started
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 1: What is Spring AI](./documentation/Topic-1-What-is-Spring-AI/README.md) | Introduction to the Spring AI framework and its role in modern application development. | Conceptual |
| [Topic 2: Key Features of Spring AI](./documentation/Topic-2-Key-Features-of-Spring-AI/README.md) | Understanding portability, structured outputs, and seamless model integrations. | Conceptual |
| [Topic 3: Integrating OpenAI with Spring Boot](./documentation/Topic-3-Integrating-OpenAI-with-Spring-Boot/README.md) | Setting up API keys and configuring basic ChatGPT API integrations. | [Topic3GeminiController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic3GeminiController.java) |
| [Topic 4: Setup Spring AI with Ollama](./documentation/Topic-4-Setup-Spring-AI-with-Ollama/README.md) | Configuring local, private, and free AI model execution using Ollama. | [Topic4OllamaController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic4OllamaController.java) |

### Phase 2: Core APIs & Internal Working
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 5: ChatClient and ChatModel APIs](./documentation/Topic-5-ChatClient-and-ChatModel-APIs/README.md) | Exploring the primary API interfaces for interacting with Generative models. | [Topic5ClientModelController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic5ClientModelController.java) |
| [Topic 6: Internal Working of ChatClient and ChatModel](./documentation/Topic-6-Internal-Working-of-ChatClient-and-ChatModel/README.md) | Deep dive into the architectural flow and abstraction layers of Spring AI. | Conceptual |
| [Topic 7: Important AI Concepts for Projects](./documentation/Topic-7-Important-AI-Concepts-for-Projects/README.md) | Understanding Tokens, Temperature, Roles, and Context Windows. | Conceptual |
| [Topic 8: Working with Multiple Models Together](./documentation/Topic-8-Working-with-Multiple-Models-Together/README.md) | Using fallback models or combining OpenAI and Google Gemini in a single app. | [Topic8MultiModelController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic8MultiModelController.java) |

### Phase 3: Prompts & Output Parsing
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 9: Output Parsing (Entities and Lists)](./documentation/Topic-9-Output-Parsing-Entities-and-Lists/README.md) | Converting raw string AI responses into strictly typed Java POJOs and Lists. | [Topic9OutputParserController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic9OutputParserController.java) |
| [Topic 10: Prompt Defaults in Spring AI](./documentation/Topic-10-Prompt-Defaults-in-Spring-AI/README.md) | Configuring global system prompts and parameters across your entire application. | [Topic10PromptDefaultsController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic10PromptDefaultsController.java) |
| [Topic 11: Dynamic Prompt Templating](./documentation/Topic-11-Dynamic-Prompt-Templating/README.md) | Preventing prompt injection and using safe placeholders in your system constraints. | [Topic11PromptTemplateController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic11PromptTemplateController.java) |
| [Topic 12: Mastering Prompt Templates](./documentation/Topic-12-Mastering-Prompt-Templates/README.md) | Loading complex prompts safely from external `.st` Resource files. | [Topic11PromptTemplateController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic11PromptTemplateController.java) |

### Phase 4: Production Preparedness & Streaming
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 13: Remember This Before Building AI Apps](./documentation/Topic-13-Remember-This-Before-Building-AI-Apps/README.md) | Best practices regarding latency, token limits, and AI unpredictability. | Conceptual |
| [Topic 16: Streaming Responses](./documentation/Topic-16-Streaming-Responses/README.md) | Using Project Reactor `Flux` to send chunked text responses for better UI perceived latency. | [Topic16StreamingController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic16StreamingController.java) |

### Phase 5: Advisors & Context (Memory)
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 14: Advisors in Spring AI](./documentation/Topic-14-Advisors-in-Spring-AI/README.md) | Utilizing interceptors to automatically manipulate prompts and intercept responses. | [Topic14AdvisorController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic14AdvisorController.java) |
| [Topic 15: Custom Advisor & Token Count](./documentation/Topic-15-Custom-Advisor-Token-Count/README.md) | Tracking API token costs and building custom guardrails using `CallAroundAdvisor`. | [Topic15CustomAdvisorController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic15CustomAdvisorController.java), [TokenUsageAdvisor.java](./src/main/java/com/example/spring_ai_demo/advisor/TokenUsageAdvisor.java), [SafeGuardAdvisor.java](./src/main/java/com/example/spring_ai_demo/advisor/SafeGuardAdvisor.java) |
| [Topic 17: Teaching LLMs to Remember (Concept)](./documentation/Topic-17-Teaching-LLMs-to-Remember/README.md) | The theory behind why LLMs have amnesia and how to fix it with historic transcripts. | Conceptual |
| [Topic 18: Implementing Chat Memory](./documentation/Topic-18-Implementing-Chat-Memory/README.md) | Injecting `MessageChatMemoryAdvisor` and `InMemoryChatMemory` into ChatClient. | [Topic18ChatMemoryController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic18ChatMemoryController.java), [ChatMemoryConfig.java](./src/main/java/com/example/spring_ai_demo/config/ChatMemoryConfig.java) |
| [Topic 19: Manage User Sessions](./documentation/Topic-19-Manage-User-Sessions/README.md) | Isolating memory state per user by dynamically evaluating unique Conversation IDs. | [Topic19MultiSessionController.java](./src/main/java/com/example/spring_ai_demo/controller/Topic19MultiSessionController.java) |
| [Topic 20: Persistent Chat Memory](./documentation/Topic-20-Persistent-Chat-Memory/README.md) | Using Database-backed repositories like `JdbcChatMemory` to survive server restarts. | Conceptual (Requires DB) |

### Phase 6: Vector Databases & RAG
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 21: Understanding RAG](./documentation/Topic-21-Understanding-RAG/README.md) | Grounding LLMs with private factual data to eliminate guessing and hallucinations. | Conceptual |
| [Topic 22: Vectors and Embeddings](./documentation/Topic-22-Vectors-and-Embeddings/README.md) | Translating textual meaning into mathematical coordinates for `VectorStores`. | Conceptual |
| [Topic 23: Setup Vector DB & Dump Embeddings](./documentation/Topic-23-Setup-Vector-DB/README.md) | Using `VectorStore.accept()` to load documents into your searchable knowledge base. | Inline in README |
| [Topic 24: Similarity Search & Manual RAG](./documentation/Topic-24-Similarity-Search-Manual-RAG/README.md) | The hard way: Using `similaritySearch` and manually packing contexts into Prompts. | Inline in README |
| [Topic 25: QuestionAnswerAdvisor (Auto-RAG)](./documentation/Topic-25-QuestionAnswerAdvisor/README.md) | The Spring way: Automating the retrieval and generation phases with a single Advisor. | Inline in README |

### Phase 7: Advanced RAG, ETL & Deployment
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 26: Advanced RAG Flow](./documentation/Topic-26-Advanced-RAG-Flow/README.md) | Introduction to Pre-retrieval routing and Post-retrieval re-ranking and filtering. | Conceptual |
| [Topic 27: Implementing RAG Pipeline](./documentation/Topic-27-Implementing-RAG-Pipeline/README.md) | Implementing advanced pipelines using Query Optimization via fast LLM calls. | Inline in README |
| [Topic 28: ETL Pipeline in Detail](./documentation/Topic-28-ETL-Pipeline-Detail/README.md) | The theory of Extracting, Transforming (chunking), and Loading AI datasets. | Conceptual |
| [Topic 29: PDF Text To Vector DB](./documentation/Topic-29-PDF-Text-To-Vector-DB/README.md) | Full Controller endpoint reading PDFs (`PagePdfDocumentReader`) and chunking payloads. | Inline in README |
| [Topic 30: Run Local AI Docker](./documentation/Topic-30-Run-Local-AI-Docker/README.md) | Complete detachment from paid APIs by securely running Ollama in a private container. | Conceptual |

### Phase 8: Agents & Function Calling
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 31: Tool Calling in LLMs](./documentation/Topic-31-Tool-Calling-in-LLMs/README.md) | Understanding how LLMs execute real-world Java functions via FunctionCallback. | Conceptual |
| [Topic 32: AI Agent using Spring AI](./documentation/Topic-32-AI-Agent-using-Spring-AI/README.md) | Building autonomous agents that orchestrate multiple tools to solve complex goals. | Conceptual |
| [Topic 33: HelpDesk Project Part 1](./documentation/Topic-33-HelpDesk-Project-Part-1/README.md) | Bootstrapping a real-world ticketing system with strict HelpBot persona constraints. | Conceptual |
| [Topic 34: HelpDesk Project Part 2](./documentation/Topic-34-HelpDesk-Project-Part-2/README.md) | Integrating database mutation tools (createTicket, checkStatus) into the ChatClient. | Conceptual |
| [Topic 35: HelpDesk Project Part 3](./documentation/Topic-35-HelpDesk-Project-Part-3/README.md) | Fixing hallucinations and isolating user sessions leveraging Chat Memory Advisors. | Conceptual |

### Phase 9: Full Stack, Multi-modal & MCP
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 36: HelpDesk Project Part 4](./documentation/Topic-36-HelpDesk-Project-Part-4/README.md) | Finishing the backend with streaming SSE Flux responses and JDBC Vector DB Memory persistence. | Conceptual |
| [Topic 37: HelpDesk Frontend React](./documentation/Topic-37-HelpDesk-Frontend-React/README.md) | Building the full-stack UI using React, Tailwind, and ShadcnUI to consume SSE streams. | Conceptual |
| [Topic 38: Audio Transcription](./documentation/Topic-38-Audio-Transcription-OpenAI/README.md) | Processing `.wav`/`.mp3` voice inputs into text using Spring AI `AudioTranscriptionModel`. | Conceptual |
| [Topic 39: Gemini Integration Guide](./documentation/Topic-39-Gemini-Integration-Guide/README.md) | Deep dive into `spring-ai-google-genai` configuration and evaluating images natively in prompts. | Conceptual |
| [Topic 40: Model Context Protocol (MCP)](./documentation/Topic-40-Model-Context-Protocol-MCP/README.md) | Architecture of MCP transport layers and auto-discovering remote agent tools securely. | Conceptual |

### Phase 10: Production Readiness & Enterprise Features
| Topic | Concept Overview | Code |
|---|---|---|
| [Topic 41: AI Observability (Micrometer)](./documentation/Topic-41-AI-Observability-Micrometer/README.md) | Tracing AI request latency and logging token/financial costs across microservices. | Conceptual |
| [Topic 42: Evaluating LLM Responses](./documentation/Topic-42-Evaluating-LLM-Responses/README.md) | Writing automated JUnit tests to ensure your AI isn't hallucinating using the Evaluation API. | Conceptual |
| [Topic 43: Resiliency & Rate Limiting](./documentation/Topic-43-Resiliency-and-Rate-Limiting/README.md) | Using Spring Retry to handle HTTP 429 Too Many Requests errors natively. | Conceptual |
| [Topic 44: Image Generation (DALL-E & Imagen)](./documentation/Topic-44-Image-Generation-DallE-Imagen/README.md) | Exploring the `ImageModel` to construct Generative Art from text descriptions. | Conceptual |
| [Topic 45: Text-to-Speech (TTS)](./documentation/Topic-45-Text-to-Speech-TTS/README.md) | Delivering voice responses from your bot converting LLM text into spoken MP3 files. | Conceptual |

---
*Curriculum index generated focusing on Spring AI architecture and advanced engineering best practices.*

# Complete Overview of Spring AI

Welcome to the ultimate Spring AI curriculum! This project contains step-by-step documentation, architectural diagrams, and interactive coding exercises designed to take you from a curious developer to a production-ready AI engineer using the Spring Ecosystem.

## 📖 Table of Contents

### Phase 1: Fundamentals & Getting Started
| Topic | Concept Overview |
|---|---|
| [Topic 1: What is Spring AI](./documentation/Topic-1-What-is-Spring-AI/README.md) | Introduction to the Spring AI framework and its role in modern application development. |
| [Topic 2: Key Features of Spring AI](./documentation/Topic-2-Key-Features-of-Spring-AI/README.md) | Understanding portability, structured outputs, and seamless model integrations. |
| [Topic 3: Integrating OpenAI with Spring Boot](./documentation/Topic-3-Integrating-OpenAI-with-Spring-Boot/README.md) | Setting up API keys and configuring basic ChatGPT API integrations. |
| [Topic 4: Setup Spring AI with Ollama](./documentation/Topic-4-Setup-Spring-AI-with-Ollama/README.md) | Configuring local, private, and free AI model execution using Ollama. |

### Phase 2: Core APIs & Internal Working
| Topic | Concept Overview |
|---|---|
| [Topic 5: ChatClient and ChatModel APIs](./documentation/Topic-5-ChatClient-and-ChatModel-APIs/README.md) | Exploring the primary API interfaces for interacting with Generative models. |
| [Topic 6: Internal Working of ChatClient and ChatModel](./documentation/Topic-6-Internal-Working-of-ChatClient-and-ChatModel/README.md) | Deep dive into the architectural flow and abstraction layers of Spring AI. |
| [Topic 7: Important AI Concepts for Projects](./documentation/Topic-7-Important-AI-Concepts-for-Projects/README.md) | Understanding Tokens, Temperature, Roles, and Context Windows. |
| [Topic 8: Working with Multiple Models Together](./documentation/Topic-8-Working-with-Multiple-Models-Together/README.md) | Using fallback models or combining OpenAI and Google Gemini in a single app. |

### Phase 3: Prompts & Output Parsing
| Topic | Concept Overview |
|---|---|
| [Topic 9: Output Parsing (Entities and Lists)](./documentation/Topic-9-Output-Parsing-Entities-and-Lists/README.md) | Converting raw string AI responses into strictly typed Java POJOs and Lists. |
| [Topic 10: Prompt Defaults in Spring AI](./documentation/Topic-10-Prompt-Defaults-in-Spring-AI/README.md) | Configuring global system prompts and parameters across your entire application. |
| [Topic 11: Dynamic Prompt Templating](./documentation/Topic-11-Dynamic-Prompt-Templating/README.md) | Preventing prompt injection and using safe placeholders in your system constraints. |
| [Topic 12: Mastering Prompt Templates](./documentation/Topic-12-Mastering-Prompt-Templates/README.md) | Loading complex prompts safely from external `.st` Resource files. |

### Phase 4: Production Preparedness & Streaming
| Topic | Concept Overview |
|---|---|
| [Topic 13: Remember This Before Building AI Apps](./documentation/Topic-13-Remember-This-Before-Building-AI-Apps/README.md) | Best practices regarding latency, token limits, and AI unpredictability. |
| [Topic 16: Streaming Responses](./documentation/Topic-16-Streaming-Responses/README.md) | Using Project Reactor `Flux` to send chunked text responses for better UI perceived latency. |

### Phase 5: Advisors & Context (Memory)
| Topic | Concept Overview |
|---|---|
| [Topic 14: Advisors in Spring AI](./documentation/Topic-14-Advisors-in-Spring-AI/README.md) | Utilizing interceptors to automatically manipulate prompts and intercept responses. |
| [Topic 15: Custom Advisor & Token Count](./documentation/Topic-15-Custom-Advisor-Token-Count/README.md) | Tracking API token costs and building custom guardrails using `CallAroundAdvisor`. |
| [Topic 17: Teaching LLMs to Remember (Concept)](./documentation/Topic-17-Teaching-LLMs-to-Remember/README.md) | The theory behind why LLMs have amnesia and how to fix it with historic transcripts. |
| [Topic 18: Implementing Chat Memory](./documentation/Topic-18-Implementing-Chat-Memory/README.md) | Injecting `MessageChatMemoryAdvisor` and `InMemoryChatMemory` into ChatClient. |
| [Topic 19: Manage User Sessions](./documentation/Topic-19-Manage-User-Sessions/README.md) | Isolating memory state per user by dynamically evaluating unique Conversation IDs. |
| [Topic 20: Persistent Chat Memory](./documentation/Topic-20-Persistent-Chat-Memory/README.md) | Using Database-backed repositories like `JdbcChatMemory` to survive server restarts. |

### Phase 6: Vector Databases & RAG
| Topic | Concept Overview |
|---|---|
| [Topic 21: Understanding RAG](./documentation/Topic-21-Understanding-RAG/README.md) | Grounding LLMs with private factual data to eliminate guessing and hallucinations. |
| [Topic 22: Vectors and Embeddings](./documentation/Topic-22-Vectors-and-Embeddings/README.md) | Translating textual meaning into mathematical coordinates for `VectorStores`. |
| [Topic 23: Setup Vector DB & Dump Embeddings](./documentation/Topic-23-Setup-Vector-DB/README.md) | Using `VectorStore.accept()` to load documents into your searchable knowledge base. |
| [Topic 24: Similarity Search & Manual RAG](./documentation/Topic-24-Similarity-Search-Manual-RAG/README.md) | The hard way: Using `similaritySearch` and manually packing contexts into Prompts. |
| [Topic 25: QuestionAnswerAdvisor (Auto-RAG)](./documentation/Topic-25-QuestionAnswerAdvisor/README.md) | The Spring way: Automating the retrieval and generation phases with a single Advisor. |

### Phase 7: Advanced RAG, ETL & Deployment
| Topic | Concept Overview |
|---|---|
| [Topic 26: Advanced RAG Flow](./documentation/Topic-26-Advanced-RAG-Flow/README.md) | Introduction to Pre-retrieval routing and Post-retrieval re-ranking and filtering. |
| [Topic 27: Implementing RAG Pipeline](./documentation/Topic-27-Implementing-RAG-Pipeline/README.md) | Implementing advanced pipelines using Query Optimization via fast LLM calls. |
| [Topic 28: ETL Pipeline in Detail](./documentation/Topic-28-ETL-Pipeline-Detail/README.md) | The theory of Extracting, Transforming (chunking), and Loading AI datasets. |
| [Topic 29: PDF Text To Vector DB](./documentation/Topic-29-PDF-Text-To-Vector-DB/README.md) | Full Controller endpoint reading PDFs (`PagePdfDocumentReader`) and chunking payloads. |
| [Topic 30: Run Local AI Docker](./documentation/Topic-30-Run-Local-AI-Docker/README.md) | Complete detachment from paid APIs by securely running Ollama in a private container. |

---
*Curriculum index generated focusing on Spring AI architecture and advanced engineering best practices.*

# Topic 23: Multi-Tenancy & Data Isolation

## Overview
When building AI SaaS products, securing data across different enterprise customers (tenants) is critical. A user from "Company A" should never retrieve RAG documents or chat memory belonging to "Company B".

## Concepts
1. **Tenant ID Injection**: Every request must carry a context of who is making it (e.g., extracted from a JWT token in the Spring Security context).
2. **Memory Isolation**: Chat Memory must be partitioned tightly by Conversation ID and Tenant ID.
3. **Vector Store Isolation**: RAG similarity searches must include metadata filtering enforcing `tenant_id = 'Company A'`.

## Real-World Analogy
Imagine renting a safe deposit box at a bank. Hundreds of customers use the same vault room (the database), but your physical key (the Tenant ID) only opens your specific box. Multi-tenancy ensures that even though everyone shares the underlying infrastructure, Customer A can never read Customer B's private AI memory or documents.

## Architecture Flow
```mermaid
flowchart TD
    Req[Incoming Request + Header: Tenant-A] --> Filter[JWT Security Filter]
    Filter --> Ctx[ThreadLocal SecurityContext]
    Ctx --> Service[AI Service]
    
    Service --> |Memory Advisor| DB[(Chat History DB)]
    DB --> |WHERE tenant = 'A'| Service
    
    Service --> |RAG Advisor| Vector[(Vector Store)]
    Vector --> |similaritySearch + filter('tenant', 'A')| Service
```

## Implementation Strategy
You can utilize metadata filtering on your `VectorStore` searches natively in Spring AI.

```java
@GetMapping("/chat")
public String chat(@RequestParam String query) {
    String tenantId = SecurityContextHolder.getContext().getAuthentication().getName(); // Retrieve tenant
    
    return chatClient.prompt()
            .user(query)
            .advisors(new QuestionAnswerAdvisor(vectorStore, 
                      SearchRequest.defaults()
                                   .withFilterExpression("tenant_id == '" + tenantId + "'")))
            .call()
            .content();
}
```

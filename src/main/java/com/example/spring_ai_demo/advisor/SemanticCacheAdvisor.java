package com.example.spring_ai_demo.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Map;

/**
 * Topic 15: Semantic Caching Advisor
 */
public class SemanticCacheAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(SemanticCacheAdvisor.class);
    private final VectorStore vectorStore;
    private final double threshold;

    public SemanticCacheAdvisor(VectorStore vectorStore, double threshold) {
        this.vectorStore = vectorStore;
        this.threshold = threshold;
    }

    @Override
    public String getName() {
        return "SemanticCacheAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        String userText = request.userText();

        SearchRequest searchRequest = SearchRequest.defaults()
                .withQuery(userText)
                .withTopK(1);

        List<Document> results = vectorStore.similaritySearch(searchRequest);

        if (!results.isEmpty()) {
            Document topResult = results.get(0);
            Double score = (Double) topResult.getMetadata().get("distance"); 
            
            log.info("[SemanticCacheAdvisor] Checking cache for: '{}'", userText);
            
            // Note: SimpleVectorStore uses Euclidean distance (lower is better)
            if (score != null && score < threshold) {
                String cachedResponse = (String) topResult.getMetadata().get("completion");
                log.info("[SemanticCacheAdvisor] --- HIT! Returning cached response (Score: {}) ---", score);
                
                ChatResponse mockResponse = new ChatResponse(List.of(new Generation(cachedResponse)));
                return new ChatClientResponse(mockResponse);
            }
        }

        log.info("[SemanticCacheAdvisor] --- MISS! Forwarding to LLM ---");

        ChatClientResponse response = chain.nextCall(request);

        String aiContent = response.content();
        if (aiContent != null) {
            log.info("[SemanticCacheAdvisor] Saving new entry to Semantic Cache");
            Document doc = new Document(userText, Map.of("completion", aiContent));
            vectorStore.add(List.of(doc));
        }

        return response;
    }
}

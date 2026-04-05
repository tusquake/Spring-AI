# Topic 29: Hybrid Search (Keyword + Vector)

## Overview
Traditional semantic search (Dense Vectors) is excellent at understanding context, but it often fails at exact keyword matches, serial numbers, or rare acronyms. **Hybrid Search** combines traditional Keyword Search (Sparse / BM25) and Semantic Search (Dense / Embeddings) to give you the best of both worlds.

## Real-World Analogy
Imagine searching for a specific employee in a massive corporate directory. Semantic Search is like asking HR: "Who is the tall guy in marketing who likes tennis?" (Concepts/Meaning). Keyword Search is like typing exactly "ID: #99482" (Exact matches). Hybrid Search relies on both strategies simultaneously to guarantee you find the exact person, whether you described their traits or typed their ID.

## Architecture Flow
```mermaid
flowchart LR
    Query[User Query 'ERROR-509 timeout'] --> Split
    Split --> Sparse[Keyword Search / BM25]
    Split --> Dense[Vector Similarity Search]
    Sparse --> DB[(Database)]
    Dense --> DB
    DB --> |Keywords Matches| RRF[Reciprocal Rank Fusion]
    DB --> |Conceptual Matches| RRF
    RRF --> |Re-Ranked & Merged Results| Output[Final Context for RAG]
```

## Concepts
1. **Sparse Retrieval (BM25)**: Evaluates term frequency. Great for specific keywords like `ERROR-CODE-509`.
2. **Dense Retrieval (Embeddings)**: Evaluates contextual meaning. Great for queries like "how do I fix a network timeout".
3. **Reciprocal Rank Fusion (RRF)**: The mathematical algorithm used to merge the results of the two searches and optimally re-rank them.

## When to Use?
Use Hybrid Search in Enterprise RAG when you need precise matching on product names alongside conceptual questions.

## Enabling it in Vector Stores
Currently, support for Hybrid search is highly dependent on your backing database natively supporting it (e.g., Pinecone, Weaviate, PostgreSQL with pgvector + pg_trgm).

If your underlying VectorStore supports it, you usually configure it via specific `SearchRequest` parameters or database-level index configurations, letting Spring AI query it seamlessly.

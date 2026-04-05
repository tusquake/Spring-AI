# Topic 40: Model Context Protocol (MCP) Explained

## Overview
As AI Agents grow more complex, maintaining hundreds of custom Java `@Bean` tools (weather, databases, JIRA, Slack, GitHub) becomes a massive engineering bottleneck. 

Enter the **Model Context Protocol (MCP)**—an open standard introduced by Anthropic. MCP acts as a universal bridge, allowing AI models to securely discover and interact with external data sources and tools *without* requiring you to wire up bespoke API clients for every single integration.

## 🧠 Architectural Paradigm Shift

Without MCP (The Old Way):
> Your Spring Boot app must manually write a `java.util.function.Function` for Jira, another for GitHub, manage the OAuth tokens for both, and expose them as tools to the LLM. 

With MCP (The New Way):
> You deploy universal **MCP Servers** (provided by the community). Your Spring Boot app acts as an **MCP Client**. Through a standard transport layer, your Spring app dynamically "discovers" everything the MCP server can do, and passes those capabilities straight to the LLM.

### The Flow Diagram

```mermaid
graph LR
    LLM((AI Model)) <-->|Prompt & Tool Calls| Spring[Spring AI (MCP Client)]
    Spring <-->|MCP Transport JSON-RPC| JiraServer[(Jira MCP Server)]
    Spring <-->|MCP Transport JSON-RPC| GitServer[(GitHub MCP Server)]
```

## 🚄 Transport Layers

MCP communicates via a standard protocol (JSON-RPC 2.0). Spring AI supports two major transport layers to connect your app to an MCP Server:

1. **Stdio (Standard Input/Output)**: The Spring Boot app spawns the MCP Server as a local sub-process. Excellent for local development or utility scripts.
2. **SSE (Server-Sent Events / HTTP)**: The MCP Server lives remotely on the internet. Your Spring Boot app connects to it via web protocols. Perfect for cloud-native microservices.

## 💻 Spring AI Implementation

In Spring AI, you configure the clients, and the framework automatically translates the remote MCP commands into local `@Tool` functions for the conversational model.

```java
// 1. Define how to connect to the MCP Server
var stdioParams = ServerParameters.builder("npx")
    .args("-y", "@modelcontextprotocol/server-postgres", "postgresql://localhost:5432/mydb")
    .build();
McpTransport transport = new StdioClientTransport(stdioParams);

// 2. Start the MCP Client
McpClient mcpClient = McpClient.sync(transport);
mcpClient.initialize();

// 3. Auto-Wire discovered MCP Tools into your ChatClient!
chatClient.prompt()
    .user("Calculate the average salary from the employee table.")
    .tools(mcpClient.listTools()) // Automatically binds all SQL capabilities!
    .call()
    .content();
```

## Summary
MCP drastically decentralizes AI architecture. It prevents Spring Boot monolithic bloat by pushing integration logic out into isolated, reusable MCP Servers, turning Spring AI into the ultimate orchestration engine.

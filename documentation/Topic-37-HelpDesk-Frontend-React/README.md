# Topic 37: Complete HelpDesk Frontend (React + Tailwind)

## Overview
A powerful Spring AI backend is only as good as the interface the user interacts with. In this module, we step away from traditional Java and build a modern, responsive frontend using **ReactJS, TailwindCSS, and ShadcnUI**.

## 🏗️ Architecture

Our frontend will consume the `Flux<String>` Server-Sent Events (SSE) endpoint we created in Topic 36, providing a seamless "typing" effect similar to ChatGPT.

### Tech Stack
1. **ReactJS**: Component-based UI formulation.
2. **TailwindCSS**: Rapid utility-first styling for dark modes and responsive layouts.
3. **ShadcnUI**: Beautiful, accessible, pre-built components (Cards, Avatars, ScrollAreas) to give the HelpDesk a premium feel.

## 💻 Consuming SSE in React (The Fetch API)

To read our Spring WebFlux streaming endpoint, we cannot use a simple `axios.get()`. We must read the ReadableStream from the standard Fetch API.

```javascript
const sendMessage = async (message) => {
  // 1. Add User message to UI
  setMessages(prev => [...prev, { text: message, sender: 'user' }]);
  
  // 2. Prepare a placeholder for the Bot's streaming reply
  let botReply = "";
  setMessages(prev => [...prev, { text: "", sender: 'bot' }]);
  
  // 3. Connect to Spring Boot Streaming Endpoint
  const response = await fetch(`http://localhost:8080/support/chat/stream?sessionId=${sessionId}&message=${message}`);
  
  // 4. Read the stream chunk-by-chunk
  const reader = response.body.getReader();
  const decoder = new TextDecoder("utf-8");
  
  while (true) {
    const { done, value } = await reader.read();
    if (done) break;
    
    // Append the new token from Spring AI to the UI
    botReply += decoder.decode(value);
    
    // Update React State to trigger a re-render with the new token
    setMessages(prev => {
        const newMsgs = [...prev];
        newMsgs[newMsgs.length - 1].text = botReply;
        return newMsgs;
    });
  }
};
```

## Important Considerations

*   **CORS**: Ensure your Spring Boot Controller is annotated with `@CrossOrigin` to allow requests from your React development server (e.g., `localhost:3000` or `localhost:5173`).
*   **State Management**: Complex Agent queries might pause while the DB is being queried by the Tool. Implement a small "Thinking..." indicator in React if the stream chunks pause for more than 2 seconds.

# Topic 42: Testing & Evaluating AI Bots (Evaluation API)

## Overview
Traditional algorithms are deterministic: `2 + 2 = 4`. You assert this easily in JUnit. 
Generative AI is **probabilistic** and non-deterministic. If you ask a bot "Why is the sky blue?", it might give a slightly different sentence every single test run. How do you automate CI/CD tests for this?

Spring AI provides the **Evaluation API**. It uses a secondary "Judge" LLM to evaluate the outputs of your primary application LLM against factual rubrics.

## 🧠 Types of Evaluators

1. **RelevancyEvaluator**: Does the AI's answer actually address the User's question? (Prevents dodging questions).
2. **FactCheckingEvaluator**: Given an authoritative text snippet (context), did the AI hallucinate facts, or did it stick strictly to the context?

## 💻 Automated Testing Pattern

Below is an example of an integration test that checks if our HelpDesk agent accurately references company refund policies without hallucinations.

```java
@SpringBootTest
class HelpDeskBotEvaluationTest {

    @Autowired
    ChatClient chatClient;
    
    @Autowired
    ChatModel judgeModel; // Often configured to a highly capable model like GPT-4o

    @Test
    void testRefundPolicyAccuracy() {
        // 1. Define the Ground Truth (The facts)
        String fact = "Cancellations made within 24 hours receive a 100% full refund.";
        String userQuestion = "I bought this 2 hours ago and want to cancel. What is my refund?";

        // 2. Execute your Application Logic
        String aiResponse = chatClient.prompt().user(userQuestion).call().content();

        // 3. Setup the Evaluator
        var evaluator = new FactCheckingEvaluator(judgeModel);

        // 4. Create Evaluation Request
        var evalRequest = new EvaluationRequest(userQuestion, List.of(fact), aiResponse);

        // 5. Evaluate and Assert!
        EvaluationResponse evalResponse = evaluator.evaluate(evalRequest);
        
        // Assert that the judge model marked this as PASS
        assertTrue(evalResponse.isPass(), 
                "AI Hallucinated! Feedback: " + evalResponse.getFeedback());
    }
}
```

## How the Judge Works
Under the hood, the `FactCheckingEvaluator` sends a complex prompt to the `judgeModel` saying: *"You are an impartial judge. Compare the AI_RESPONSE to the SOURCE_FACT. Answer ONLY true or false if the AI_RESPONSE is factually supported by SOURCE_FACT."*

## Summary
The Evaluation API shifts AI testing from manual "vibe checks" to automated, mathematical verifications. You can safely merge Pull Requests knowing your Agent's behavior hasn't drifted or degraded into hallucination loops.

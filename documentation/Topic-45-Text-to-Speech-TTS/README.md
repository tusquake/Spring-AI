# Topic 45: Text-to-Speech (TTS)

## Overview
In Topic 38, we taught our application how to "hear" audio and turn it into text. In this final topic, we complete the conversational loop: transforming the generated text back into lifelike human audio using **Text-To-Speech (TTS)**.

By combining Speech Models, the `ChatClient`, and Audio Transcription, you can build a complete **Voice-to-Voice AI Assistant** purely in Java.

## 🧠 The Architecture (SpeechModel)

Spring AI abstracts TTS engines (like OpenAI's `tts-1`) through the `SpeechModel` interface. You feed it a `SpeechPrompt` containing raw text, and it returns a byte stream of the generated MP3 file.

## 💻 Implementation Pattern

### Generating an Audio File API

```java
@RestController
@RequestMapping("/voice")
public class VoiceController {

    private final SpeechModel speechModel;

    public VoiceController(SpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    /**
     * Produces 'audio/mpeg' so the browser can play it directly in an HTML5 <audio> tag.
     */
    @GetMapping(value = "/speak", produces = "audio/mpeg")
    public byte[] synthesizeVoice(@RequestParam String text) {
        
        // 1. Configure the voice actor and speed
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
            .withModel("tts-1")
            .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY) // Different voices: Alloy, Echo, Fable
            .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
            .withSpeed(1.0f)
            .build();

        // 2. Create the Prompt
        SpeechPrompt prompt = new SpeechPrompt(text, options);

        // 3. Generate Audio
        SpeechResponse response = speechModel.call(prompt);
        
        // 4. Return the raw MP3 byte payload
        return response.getResult().getOutput();
    }
}
```

## The "Ultimate" Voice Assistant Flow

If you wanted to combine everything learned in this course into a single API endpoint:

1. `AudioTranscriptionModel` receives a user's voice message `.mp3` and parses it into `"What is Spring AI?"`
2. `ChatClient` (with Memory, Tools, and Vector Database Advisors) queries knowledge bases and replies securely: `"Spring AI is a Java framework..."`
3. `SpeechModel` takes the LLM text reply and converts it into a new `.mp3` file.
4. Your React frontend plays the `.mp3`.

## Summary
Text-to-Speech completes the Spring AI masterclass toolkit. You now have complete mastery over Text, Vision, Audio, Tool Automation, Memory Isolation, Observability, and RAG architectures in the Spring domain. You are ready to build the future.

package com.example.langchainj.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface Assistant {

    String chat(String userMessage);

    String chat(@MemoryId String userId, @UserMessage String message);
}
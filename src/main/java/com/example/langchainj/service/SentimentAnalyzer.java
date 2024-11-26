package com.example.langchainj.service;

import dev.langchain4j.service.UserMessage;

public interface SentimentAnalyzer {

    @UserMessage("Does {{it}} has a positive sentiment?")
    boolean isPositive(String text);
}
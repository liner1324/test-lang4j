package com.example.langchainj.service;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface Chat {
    String chat(String text);
}
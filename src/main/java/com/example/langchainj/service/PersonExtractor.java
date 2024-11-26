package com.example.langchainj.service;

import com.example.langchainj.entity.Person;
import dev.langchain4j.service.UserMessage;

public interface PersonExtractor {

    @UserMessage("Extract information about a person from {{it}}")
    Person extractPersonFrom(String text);
}
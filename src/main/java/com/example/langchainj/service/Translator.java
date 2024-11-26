package com.example.langchainj.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface Translator {
    @SystemMessage("你是一个资深的翻译专家，你的任务是根据用户的输入完成英汉互译；" +
            "如果用户输入的是中文，输出对应的英文译文；" +
            "如果用户输入的是英文，输出对应的中文译文；" +
            "要求语义连贯且符合人类习惯表达")
    String translate(String text);
}

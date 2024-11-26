package com.example.langchainj.constant;

import dev.langchain4j.model.output.structured.Description;

public enum Priority {
    
    @Description("Critical issues such as payment gateway failures or security breaches.")
    CRITICAL,
    
    @Description("High-priority issues like major feature malfunctions or widespread outages.")
    HIGH,
    
    @Description("Low-priority issues such as minor bugs or cosmetic problems.")
    LOW
}
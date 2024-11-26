package com.example.langchainj.entity;

import org.bsc.langgraph4j.state.AgentState;

import java.util.Map;
import java.util.Optional;

public class State extends AgentState {

  public State(Map<String, Object> initData) {
      super( initData  );
  }

  public Optional<String> input() {
      return value("input");
  }
  Optional<String> results() {
      return value("results");
  }
 
}

package com.example.langchainj;

import com.example.langchainj.entity.Person;
import com.example.langchainj.entity.State;
import com.example.langchainj.service.Assistant;
import com.example.langchainj.service.PersonExtractor;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphStateException;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.action.AsyncEdgeAction;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.state.AgentState;
import org.bsc.langgraph4j.state.AppenderChannel;
import org.bsc.langgraph4j.state.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.action.AsyncEdgeAction.edge_async;
import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;
import static org.bsc.langgraph4j.utils.CollectionsUtils.mapOf;

@SpringBootTest
@Slf4j
class LangchainJApplicationTests {

    @Test
    void contextLoads() throws GraphStateException {
        String apiKey = "demo";
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .build();
        AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10));
        StateGraph<MessagesState> stateGraph = new StateGraph<>(MessagesState.SCHEMA, MessagesState::new);

        AsyncEdgeAction<MessagesState> edgeAction = edge_async(state -> "first");
        stateGraph.addConditionalEdges("nodeA", edgeAction, mapOf( "first","nodeB","second","nodeC"));
        CompiledGraph<MessagesState> compiledGraph = stateGraph.compile();

        AsyncNodeAction<State> myNode = node_async(state -> {
            System.out.println( "In myNode: " );
            return state.data();
        });
//        StateGraph<MyState> graph = new StateGraph<>(MessagesState.SCHEMA, MyState::new);
//
//        AsyncNodeAction<State> myNode = node_async(state -> {
//            System.out.println( "In myNode: " );
//            return mapOf();
//        });
//        AsyncNodeAction<State> myOtherNode = node_async(state -> {
//            System.out.println( "In myOtherNode: " );
//            return mapOf();
//        });
//        StateGraph<State> graph1 = new StateGraph<State>(State::new);
//        graph1.addNode("myNode", myNode);
//        graph1.addNode("myOtherNode", myOtherNode);
//        graph1.addEdge(START, "nodeA");
//        graph1.addEdge("nodeA", END);
//
//        AsyncEdgeAction<AgentState> edgeAction = edge_async(state -> {
//            return "";
//        });
//
//        graph1.addConditionalEdges("nodeA", state -> CompletableFuture.completedFuture("test"),
//                mapOf());
    }

    static class MessagesState extends AgentState {

        static Map<String, Channel<?>> SCHEMA = mapOf(
                "messages", AppenderChannel.<String>of(ArrayList::new)
        );

        public MessagesState(Map<String, Object> initData) {
            super(initData);
        }
    }

    static class MyState extends AgentState {

        static Map<String, Channel<?>> SCHEMA = mapOf(
                "property", Channel.<String>of( ( oldValue, newValue ) -> newValue.toUpperCase() ),
                "person", Channel.<String>of(( oldValue, newValue) -> newValue),
                "personExtracted", null

        );

        /**
         * Constructs an AgentState with the given initial data.
         *
         * @param initData the initial data for the agent state
         */
        public MyState(Map<String, Object> initData) {
            super(initData);
        }
    }
}

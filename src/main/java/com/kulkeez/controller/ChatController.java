package com.kulkeez.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The class is annotated with @RestController, meaning it’s ready for use by Spring MVC to handle web requests. 
 * 
 * @author <a href="mailto:kulkeez@yahoo.com">Vikram Kulkarni</a>
 * 
 */
@Slf4j
@RestController
public class ChatController {

    private final ChatClient chatClient;

    private final ChatModel chatModel;

    private final VectorStore vectorStore;

    private String prompt = """
        Your task is to answer the questions about Apache JMeter. Use the information from the DOCUMENTS
        section to provide accurate answers. If unsure or if the answer isn't found in the DOCUMENTS section, 
        simply state that you don't know the answer.
                    
        QUESTION:
        {input}
                    
        DOCUMENTS:
        {documents}
                    
        """;

    /**
     * Constructor based dependency injection
     * 
     * @param chatClient
     */
    public ChatController(ChatClient.Builder builder, 
                ChatModel chatModel, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;        
    }

   /**
    * Invoke it as /jmeter?question=List all elements of a Test Plan within Apache JMeter
    * 
    * @param question
    * @return
    */     
    @GetMapping("/jmeter")
    public String simplify(@RequestParam(value = "question",
    defaultValue = "List all elements of a Test Plan within Apache JMeter")
                           String question) {
        log.debug("simplify() called.");
        PromptTemplate template = new PromptTemplate(prompt);
        Map<String, Object> promptsParameters = new HashMap<>();
        promptsParameters.put("input", question);
        promptsParameters.put("documents", findSimilarData(question));

        return chatModel
                .call(template.create(promptsParameters))
                .getResult()
                .getOutput()
                .getContent();
    }

    /**
     * Invoke it as /twister?message=Tell me a tongue twister
     * 
     * @param message
     * @return
     */
    @GetMapping("/twister")
    public String tongueTwister(@RequestParam(value = "message", defaultValue = "Tell me a tongue twister") String message) {
        log.debug("tongueTwister() called.");
        return chatClient.prompt()
                .user(message)
                .call()
                .content(); // short for getResult().getOutput().getContent();
    }

    /**
     * Invoke it as /twister-by-language?lang=English
     * 
     * @param lang
     * @return
     */
    @GetMapping("/twister-by-language")
    public String twisterByLanguage(@RequestParam String lang) {
        log.debug("twisterByLanguage() called.");
        return chatClient.prompt()
                .user(u -> u.text("Tell me a tongue twister in {lang}").param("lang",lang))
                .call()
                .content();
    }


    /**
     * Invoke it as /twister-with-response?message=Tell me a tounge twister    
     * 
     * @param message
     * @return
     */
    @GetMapping("twister-with-response")
    public ChatResponse twisterWithResponse(@RequestParam(value = "message", defaultValue = "Tell me a tounge twister") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
    }


    /**
     * Perform similarity search using the VectorStore
     */
    private String findSimilarData(String question) {
        log.debug("Performing similarity search using the Vector Store...");
        List<Document> documents =
                vectorStore.similaritySearch(SearchRequest
                .query(question)
                .withTopK(5));

        return documents
                .stream()
                .map(document -> document.getContent().toString())
                .collect(Collectors.joining());

    }
}
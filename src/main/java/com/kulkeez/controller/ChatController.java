package com.kulkeez.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * The class is annotated with @RestController, meaning it’s ready for use by Spring MVC to handle web requests. 
 * 
 * @author <a href="mailto:kulkeez@yahoo.com">Vikram Kulkarni</a>
 * 
 */
@RestController
public class ChatController {

    private final ChatClient chatClient;

    /**
     * Constructor based dependency injection
     * 
     * @param chatClient
     */
    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Invoke it as /twister?message=Tell me a tongue twister
     * 
     * @param message
     * @return
     */
    @GetMapping("/")
    public String tongueTwister(@RequestParam(value = "message", defaultValue = "Tell me a tongue twister") String message) {
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

}
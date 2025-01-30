package com.kulkeez.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

/**
 * 
 * The class is flagged as a @RestController, meaning it’s ready for use by Spring MVC to handle web requests.
 * 
 * @author <a href="mailto:kulkeez@yahoo.com">Vikram Kulkarni</a>  
 */
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final ChatClient chatClient;

    /**
     * Constructor based dependency injection
     * 
     * @param chatClient
     */
    public RecipeController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/ideas")
    public List<String> getRecipeIdeas(@RequestBody List<String> ingredients) {

        String userPrompt = String.format(
                "Here is a list of ingredients: %s.\n" +
                        "Suggest a few creative recipe name ideas. Only respond with recipe names, one per line.",
                ingredients
        );

        // system context
        String response = chatClient.prompt()
                .system("You are a creative chef. Provide recipe name ideas based on the user's ingredients.")
                .user(userPrompt)
                .call()
                .content();

        String[] recipeNames = response.split("\\r?\\n");
        return Arrays.asList(recipeNames);
    }
}
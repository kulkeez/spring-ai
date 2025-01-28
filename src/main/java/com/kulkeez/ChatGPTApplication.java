package com.kulkeez;

import java.util.Arrays;
import java.util.Locale;
import java.text.NumberFormat;
import java.sql.Timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 
 * Entry program to launch this Spring Boot application.
 * 
 * @author <a href="mailto:kulkeez@yahoo.com">Vikram Kulkarni</a>
 * 
 */
@SpringBootApplication
@Slf4j
public class ChatGPTApplication {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Timestamp tOne = new Timestamp(System.currentTimeMillis());

		log.info("Launching the Spring Data application ...");
		SpringApplication.run(ChatGPTApplication.class, args);

		log.info("Launched Spring Data application at time: {} ", tOne);
	}

	/**
	 * This method runs on start up, retrieves all the beans that were created either by your app or 
	 * were automatically added thanks to Spring Boot. It sorts them and prints them out.
	 * 
	 * The @Bean annotation used below tells Spring that this method will return a CommandLineRunner object 
	 * that should be registered as a bean in the Spring application context.
	 * 
	 * @param ctx
	 * @return
	 */
	@Bean
	@Description("Upon Spring start up, retrieves all the beans that were created either by our app or were automatically added thanks to Spring Boot.")
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            log.info("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);

            for (String beanName : beanNames) {
                log.debug(beanName);
            }
            log.info("Total Beans available in the Spring container: " + beanNames.length);

        };
    }


	@Bean
    public NumberFormat defaultNumberFormat() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault());
    }
}

package com.youchocolatier.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import com.youchocolatier.bot.core.BotYouChocolatier;

@SpringBootApplication
public class TelegramBotApplication {

	public static void main(String[] args) {
		
		ApiContextInitializer.init();
		SpringApplication.run(TelegramBotApplication.class, args);	
				
		TelegramBotsApi botsApi = new TelegramBotsApi();
		
        try {
            botsApi.registerBot(new BotYouChocolatier());  
        } catch (TelegramApiException e) {
        	BotLogger.error("YOUCHOCOLATIER", e);
        }
	}

}

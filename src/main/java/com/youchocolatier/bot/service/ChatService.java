package com.youchocolatier.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;

@Service
public class ChatService {

	public SendChatAction acaoDigitarTexto(Long chatId) {
		SendChatAction acao = new SendChatAction();
		
		acao.setChatId(chatId);
		acao.setAction(ActionType.TYPING);
				
		return acao;
	}
	
	public SendChatAction acaoCarregarImagem(Long chatId) {
		SendChatAction acao = new SendChatAction();
		
		acao.setChatId(chatId);
		acao.setAction(ActionType.UPLOADPHOTO);
				
		return acao;
	}
	
	public SendChatAction acaoCarregarArquivo(Long chatId) {
		SendChatAction acao = new SendChatAction();
		
		acao.setChatId(chatId);
		acao.setAction(ActionType.UPLOADDOCUMENT);
				
		return acao;
	}
}

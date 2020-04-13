package com.youchocolatier.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class MensagemService {

	public SendMessage enviarMensagem(Long chatId, String texto, String formatacao) {
		SendMessage mensagem = new SendMessage();
		
		mensagem.setChatId(chatId);
		mensagem.setText(texto);
		mensagem.setParseMode(formatacao);
		
		return mensagem;
	}
}

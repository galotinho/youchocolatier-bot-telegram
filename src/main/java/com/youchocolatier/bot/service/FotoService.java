package com.youchocolatier.bot.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Service
public class FotoService {

	public SendPhoto enviarImagem(Long chatId, File arquivo) {
		SendPhoto imagem = new SendPhoto();
		
		imagem.setChatId(chatId);
		imagem.setPhoto(arquivo);
		
		return imagem;
	}
}

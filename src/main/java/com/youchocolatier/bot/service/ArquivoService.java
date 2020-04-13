package com.youchocolatier.bot.service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Service
public class ArquivoService {

	public SendDocument enviarArquivo(Long chatId, File arquivo, String mensagem, InputFile miniatura) {
		SendDocument documento = new SendDocument();
		
		documento.setChatId(chatId);
		documento.setDocument(arquivo);
		documento.setCaption(mensagem);	
		documento.setThumb(miniatura);
		
		return documento;
	}
}

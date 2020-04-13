package com.youchocolatier.bot.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import com.youchocolatier.bot.model.Subscriber;
import com.youchocolatier.bot.service.ArquivoService;
import com.youchocolatier.bot.service.ChatService;
//import com.youchocolatier.bot.service.FotoService;
import com.youchocolatier.bot.service.MensagemService;
import com.youchocolatier.bot.service.SubscriberService;

@Component
public class BotYouChocolatier extends TelegramLongPollingBot{

	private static final String LOGTAG = "YOUCHOCOLATIER";
			
	@Autowired
	private MensagemService mensagemService;
	
	@Autowired
	private SubscriberService subscriberService;
	
	@Autowired
	private ArquivoService arquivoService;
	
	@Autowired
	private ChatService chatService;	
	
	@Value("${telegram.token}")
	private String token;

	@Override
	public String getBotUsername() {
		return "You Chocolatier";
	}

	@Override
	public String getBotToken() {		
		return token;
	}
		
	@Override
	public void onUpdateReceived(Update update){
			
		if (update.hasMessage() && update.getMessage().hasText()) {			
			if(update.getMessage().getText().equals("/start")) {
				fluxoStart(update);			
			}else {
				if(update.getMessage().getText().toLowerCase().contains("quero")) {
					fluxoComprarEbook(update);
				}				
			}
	    }			
	}

	private void fluxoStart(Update update) {
		
		Long chatId = update.getMessage().getChatId();		
		String nome = update.getMessage().getFrom().getFirstName();
		String sobrenome = update.getMessage().getFrom().getFirstName();
		String usuario = update.getMessage().getFrom().getUserName();
		Integer userId = update.getMessage().getFrom().getId();			
		
		Subscriber subscriber = new Subscriber(chatId, nome, sobrenome, usuario, userId, 1);
		subscriberService.salvar(subscriber);
		
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("QUERO");
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        
        try {
        		execute(chatService.acaoDigitarTexto(chatId));
        		execute(mensagemService.enviarMensagem(chatId, getTextoBoasVindas(nome), ParseMode.HTML)); 
        		
           		execute(chatService.acaoCarregarArquivo(chatId));
	            execute(arquivoService.enviarArquivo(chatId, new File(getClass().getClassLoader().getResource("static/pdfs/Formulacao-ao-leite-castanha.pdf").getFile()), 
	            		"BAIXE CLICANDO AQUI!", new InputFile(new File(getClass().getClassLoader().getResource("static/imagens/formulacao-chocolate-castanha.png").getFile()), "PDF")));
	            
	            execute(mensagemService.enviarMensagem(chatId, "Esse material é do seu interesse? Quer mais formulações como essa? Clica aqui em baixo 👇 em QUERO ou escreva QUERO!", ParseMode.HTML).setReplyMarkup(replyKeyboardMarkup));
            
        } catch (TelegramApiException e) {
        	BotLogger.error(LOGTAG, e);
        } 
		
	}
	
	private void fluxoComprarEbook(Update update) {
			
		Long chatId = update.getMessage().getChatId();	
		
		Subscriber subscriber = subscriberService.buscarPorId(chatId);
		subscriber.setMsgId(2);
		subscriberService.salvar(subscriber);
		
		ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setSelective(true);
		
		try {
        	execute(chatService.acaoDigitarTexto(chatId));
            execute(mensagemService.enviarMensagem(chatId, getTextoVendaEbook(), ParseMode.HTML).setReplyMarkup(replyKeyboardRemove));
		} catch (TelegramApiException e) {
			BotLogger.error(LOGTAG, e);
		}
	}

	public String getTextoBoasVindas(String nome){
		
		return  "Olá "+nome+", tudo bem?! Muito legal te ter aqui no nosso canal do TELEGRAM!\r\n" + 
        		"\r\n" + 
        		"Mas antes, deixa eu te explicar como vai funcionar a nossa comunicação aqui, assim você não corre o risco de perder nenhum conteúdo. ⤵️\r\n" + 
        		"\r\n" + 
        		"Funciona assim, aqui na nossa conversa do Telegram eu vou sempre te enviar conteúdos gratuitos que considero \"de muita importância\"...\r\n" + 
        		"\r\n" + 
        		"Ou seja, materiais que vão facilitar muito a sua jornada na elaboração e produção de chocolates, então fique atento e não perca nenhuma mensagem que eu te mandar aqui.\r\n" + 
        		"\r\n" + 
        		"Mas agora pega essa formulação de um dos nossos chocolates mais \"vendáveis\"!\r\n";
	}
	
	public String getTextoVendaEbook() {
		
		return  "Que bom que você tem interesse em formulações e informações nutricionais como essa que te enviei.\r\n" + 
				"\r\n" + 
				"Clique no link abaixo!\r\n" + 
				"\r\n" + 
				"https://youchocolatier.com/index.php/formulacao/\r\n" + 
				"\r\n" + 
				"Lá você pode ter acesso a 05 formulações (alguns dos chocolates mais comercializadas por nós).\r\n" + 
				"\r\n" + 
				"Além disso para mostrar nossa gratidão você receberá grandes descontos nos nossos cursos que mais te interessar <b>(dependendo do valor, o curso poderá sair de graça!).</b>\r\n" + 
				"\r\n" + 
				"Essa é uma grande oportunidade!\r\n" + 
				"\r\n" + 
				"Abraço";
	}

}

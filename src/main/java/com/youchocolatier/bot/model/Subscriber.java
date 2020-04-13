package com.youchocolatier.bot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity	
@Table(name = "subscribers")
public class Subscriber {

	@Id 
	@EqualsAndHashCode.Include
	private Long chatId;
	private String nome;
	private String sobrenome;
	private String usuario;
	private Integer userId;
	private Integer msgId;
	
	public Subscriber() {
		
	}
	
	public Subscriber(Long chatId, String nome, String sobrenome, String usuario, Integer userId, Integer msgId) {
		super();
		this.chatId = chatId;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.usuario = usuario;
		this.userId = userId;
		this.msgId = msgId;
	}
	
}

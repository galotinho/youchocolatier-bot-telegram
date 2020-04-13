package com.youchocolatier.bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youchocolatier.bot.model.Subscriber;
import com.youchocolatier.bot.repository.SubscriberRepository;

@Service
public class SubscriberService {

	@Autowired
	private SubscriberRepository subscriberRepository;
	
	@Transactional(readOnly=false)
	public void salvar(Subscriber subscriber) {
		subscriberRepository.save(subscriber);		
	}
	
	@Transactional(readOnly=true)
	public Subscriber buscarPorId(Long id) {
		return subscriberRepository.findById(id).get();
	}

	@Transactional(readOnly=false)
	public void remover(Long id) {
		subscriberRepository.deleteById(id);		
	}
	
	@Transactional(readOnly=true)
	public List<Subscriber> buscarTodos() {
		return subscriberRepository.findAll();
	}
}

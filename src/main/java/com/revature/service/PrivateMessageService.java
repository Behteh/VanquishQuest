package com.revature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.repository.PrivateMessageRepository;
import com.revature.repository.entity.PrivateMessage;

@Service
public class PrivateMessageService {

	PrivateMessageRepository privateMessageRepository;

	@Autowired
	public PrivateMessageService(PrivateMessageRepository privateMessageRepository) {
		this.privateMessageRepository = privateMessageRepository;
	}


	public Optional<PrivateMessage> findById(Long id)
	{
		return privateMessageRepository.findById(id);
	}
	
	public PrivateMessage save(PrivateMessage privateMessage)
	{
		return privateMessageRepository.save(privateMessage);
	}
	
	public void deleteById(Long id)
	{
		privateMessageRepository.deleteById(id);
	}
	
	public Optional<List<PrivateMessage>> getRecentMessages(long id)
	{
		Optional<List<PrivateMessage>> messages = privateMessageRepository.findTop20ByToUserId(id);
		return messages;
	}
	
	public Optional<List<PrivateMessage>> searchMessages(long character_id, String keywords)
	{
		Optional<List<PrivateMessage>> pms = privateMessageRepository.searchMessages(keywords, character_id);
		return pms;
	}
}

package com.revature.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.repository.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

	@Query(value = "SELECT * FROM chat_messages ORDER BY  timestamp ASC LIMIT 100", nativeQuery = true)
	public Optional<List<ChatMessage>> getRecentMessages();

	@Query(value = "SELECT * FROM chat_messages WHERE message iLIKE %:keywords% ORDER BY timestamp DESC LIMIT 20", nativeQuery = true)
	public Optional<List<ChatMessage>> searchMessages(String keywords);
	
	@Query(value = "INSERT INTO chat_messages (character_id, message) VALUES (:character_id, :message)", nativeQuery = true)
	public ChatMessage createMessage(long character_id, String message);
}

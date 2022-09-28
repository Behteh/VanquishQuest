package com.revature.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.repository.entity.ChatMessage;
import com.revature.repository.entity.PrivateMessage;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {

	public Optional<List<PrivateMessage>> findTop20ByToUserId(long id);
	
	@Query(value = "SELECT * FROM private_messages WHERE message iLIKE %:keywords% AND to_user_id = :id ORDER BY timestamp DESC LIMIT 20", nativeQuery = true)
	public Optional<List<PrivateMessage>> searchMessages(String keywords, long id);}

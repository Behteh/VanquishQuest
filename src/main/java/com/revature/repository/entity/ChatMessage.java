package com.revature.repository.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long message_id;
	
	private long character_id;
	
	private String message;
	
	private LocalDateTime timestamp;

	public ChatMessage() {
		super();
		this.timestamp = LocalDateTime.now();
	}

	public ChatMessage(long message_id, long character_id, String message, LocalDateTime timestamp) {
		super();
		this.message_id = message_id;
		this.character_id = character_id;
		this.message = message;
		this.timestamp = timestamp;
	}

	public long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(long message_id) {
		this.message_id = message_id;
	}

	public long getCharacter_id() {
		return character_id;
	}

	public void setCharacter_id(long character_id) {
		this.character_id = character_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(character_id, message, message_id, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatMessage other = (ChatMessage) obj;
		return character_id == other.character_id && Objects.equals(message, other.message)
				&& message_id == other.message_id && Objects.equals(timestamp, other.timestamp);
	}

	@Override
	public String toString() {
		return "ChatMessage [message_id=" + message_id + ", character_id=" + character_id + ", message=" + message
				+ ", timestamp=" + timestamp + "]";
	}
	
	
}

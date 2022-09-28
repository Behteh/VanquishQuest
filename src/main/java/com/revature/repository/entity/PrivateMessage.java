package com.revature.repository.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "private_messages")
public class PrivateMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long message_id;
	
	@Column(name = "from_user_id")
	private long fromUserId;
	
	@Column(name = "to_user_id")
	private long toUserId;
	
	private String topic;
	
	private String message;
	
	@Column(name="timestamp")
	private LocalDateTime timestamp;

	public PrivateMessage() {
		super();
	}

	public PrivateMessage(long message_id, long fromUserId, long toUserId, String topic, String message,
		LocalDateTime timestamp) {
		this.message_id = message_id;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.topic = topic;
		this.message = message;
		this.timestamp = timestamp;
	}

	public long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(long message_id) {
		this.message_id = message_id;
	}

	public long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public long getToUserId() {
		return toUserId;
	}

	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
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
		return Objects.hash(fromUserId, message, message_id, timestamp, toUserId, topic);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrivateMessage other = (PrivateMessage) obj;
		return fromUserId == other.fromUserId && Objects.equals(message, other.message)
				&& message_id == other.message_id && Objects.equals(timestamp, other.timestamp)
				&& toUserId == other.toUserId && Objects.equals(topic, other.topic);
	}

	@Override
	public String toString() {
		return "PrivateMessage [message_id=" + message_id + ", fromUserId=" + fromUserId + ", toUserId=" + toUserId
				+ ", topic=" + topic + ", message=" + message + ", timestamp=" + timestamp + "]";
	}
	
}

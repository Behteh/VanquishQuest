package com.revature.repository.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "friend_requests")
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long request_id;
	
	private long sender_id;
	
	private long receiver_id;

	public FriendRequest() {
		super();
	}

	public FriendRequest(long request_id, long sender_id, long receiver_id) {
		super();
		this.request_id = request_id;
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
	}

	public long getRequest_id() {
		return request_id;
	}

	public void setRequest_id(long request_id) {
		this.request_id = request_id;
	}

	public long getSender_id() {
		return sender_id;
	}

	public void setSender_id(long sender_id) {
		this.sender_id = sender_id;
	}

	public long getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(long receiver_id) {
		this.receiver_id = receiver_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(receiver_id, request_id, sender_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendRequest other = (FriendRequest) obj;
		return receiver_id == other.receiver_id && request_id == other.request_id && sender_id == other.sender_id;
	}

	@Override
	public String toString() {
		return "FriendRequest [request_id=" + request_id + ", sender_id=" + sender_id + ", receiver_id=" + receiver_id
				+ "]";
	}
}

package com.revature.repository.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "game_user")
public class GameUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;

	private String username;
	
	private String password;
	
	private boolean admin;
	
	private String user_title;
	
	private String avatar_url;

	public GameUser() {
		super();
	}

	public GameUser(long user_id, String username, String password, boolean admin, String user_title,
			String avatar_url) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.user_title = user_title;
		this.avatar_url = avatar_url;
	}

	public GameUser(String username, String password) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.password = password;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getUser_title() {
		return user_title;
	}

	public void setUser_title(String user_title) {
		this.user_title = user_title;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	@Override
	public int hashCode() {
		return Objects.hash(admin, avatar_url, password, user_id, user_title, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameUser other = (GameUser) obj;
		return admin == other.admin && Objects.equals(avatar_url, other.avatar_url)
				&& Objects.equals(password, other.password) && user_id == other.user_id
				&& Objects.equals(user_title, other.user_title) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "GameUser [user_id=" + user_id + ", username=" + username + ", password=" + password + ", admin=" + admin
				+ ", user_title=" + user_title + ", avatar_url=" + avatar_url + "]";
	}
	
	
}

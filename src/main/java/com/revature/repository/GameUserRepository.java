package com.revature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.repository.entity.GameUser;


public interface GameUserRepository extends JpaRepository <GameUser, Long> {

	public Optional<GameUser> findGameUserByUsernameAndPassword(String username, String password);

	public Optional<GameUser> findGameUserByUsername(String username);
	
	@Query(value = "INSERT INTO game_user (\"username\", \"password\") VALUES (:username, :password)", nativeQuery = true)
	public void createByUsernameAndPassword(String username, String password);
}

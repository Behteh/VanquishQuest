package com.revature.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.repository.entity.CharacterSheet;

public interface CharacterSheetRepository extends JpaRepository<CharacterSheet, Long> {

	public boolean existsByName(String name);

	@Query(value="SELECT * FROM character_sheet WHERE user_id = :user_id LIMIT 1", nativeQuery=true)
	public Optional<CharacterSheet> getUserById(long user_id);

}
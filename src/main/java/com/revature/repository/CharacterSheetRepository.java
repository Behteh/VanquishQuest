package com.revature.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.repository.entity.CharacterSheet;

public interface CharacterSheetRepository extends JpaRepository<CharacterSheet, Long> {

	public boolean existsByName(String name);

}
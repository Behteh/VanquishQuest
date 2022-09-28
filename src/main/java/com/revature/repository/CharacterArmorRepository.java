package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.repository.entity.CharacterArmor;
import com.revature.repository.entity.CharacterWeapons;

public interface CharacterArmorRepository extends JpaRepository<CharacterArmor, Long> {
	public List<CharacterArmor> findAllByCharacterId(long id);

}

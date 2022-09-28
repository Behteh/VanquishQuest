package com.revature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.repository.entity.Weapon;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {
	
}
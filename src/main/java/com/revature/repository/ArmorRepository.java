package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.repository.entity.Armor;

public interface ArmorRepository extends JpaRepository<Armor, Long> {

}
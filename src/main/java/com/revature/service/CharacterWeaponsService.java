package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.repository.CharacterWeaponsRepository;
import com.revature.repository.WeaponRepository;
import com.revature.repository.entity.CharacterWeapons;
import com.revature.repository.entity.Weapon;

@Service
public class CharacterWeaponsService {

	private CharacterWeaponsRepository repo;
	private WeaponRepository weaponRepository;
	
	
	@Autowired
	public CharacterWeaponsService(CharacterWeaponsRepository repo, WeaponRepository weaponRepository) {
		this.repo = repo;
		this.weaponRepository = weaponRepository;
	}

	public ArrayList<Weapon> findWeaponsByCharacterId(long id)
	{
		List<CharacterWeapons> myList = repo.findAllByCharacterId(id);
		ArrayList<Weapon> weapons = new ArrayList<>();
		for(CharacterWeapons o : myList)
		{
			Optional<Weapon> weapon = weaponRepository.findById(o.getWeapon_id());
			weapons.add(weapon.get());
		}
		return weapons;
	}

}

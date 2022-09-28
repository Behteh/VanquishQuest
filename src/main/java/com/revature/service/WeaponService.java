package com.revature.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.NoArmorsException;
import com.revature.exceptions.NoWeaponsException;
import com.revature.repository.WeaponRepository;
import com.revature.repository.entity.Armor;
import com.revature.repository.entity.Weapon;

@Service
public class WeaponService {

	WeaponRepository weaponRepository;

	@Autowired
	public WeaponService(WeaponRepository weaponRepository) {
		this.weaponRepository = weaponRepository;
	}
	
	public Optional<Weapon> findById(Long id)
	{
		return weaponRepository.findById(id);
	}
	
	public ArrayList<Weapon> findAll() throws NoWeaponsException
	{
		ArrayList<Weapon> weaponList = (ArrayList<Weapon>) weaponRepository.findAll();
		if(weaponList.size() == 0)
		{
			throw new NoWeaponsException("There are no weapons in the game");
		}
		return weaponList;
	}
	
	public Weapon save(Weapon weapon)
	{
		return weaponRepository.save(weapon);
	}
	
	public void deleteById(Long id)
	{
		weaponRepository.deleteById(id);
	}
	
}

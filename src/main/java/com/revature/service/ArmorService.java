package com.revature.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.NoArmorsException;
import com.revature.repository.ArmorRepository;
import com.revature.repository.entity.Armor;

@Service
public class ArmorService {

	private ArmorRepository armorRepository;

	@Autowired
	public ArmorService(ArmorRepository armorRepository) {
		this.armorRepository = armorRepository;
	}
	
	public ArrayList<Armor> findAll() throws NoArmorsException
	{
		ArrayList<Armor> armorList = (ArrayList<Armor>) armorRepository.findAll();
		if(armorList.size() == 0)
		{
			throw new NoArmorsException("There are no armors in the game");
		}
		return armorList;
	}
	
	public Optional<Armor> findById(Long id)
	{
		return armorRepository.findById(id);
	}
	
	public Armor save(Armor armor)
	{
		return armorRepository.save(armor);
	}
	
	public void deleteById(Long id)
	{
		armorRepository.deleteById(id);
	}
	
}

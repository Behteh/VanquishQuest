package com.revature.repository.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "character_sheet")
public class CharacterSheet {
	
	/**
	 * @Id annotation marks a field as the primary key. This should be unique.
	 * @GeneratedValue denotes how a value is generated. In most cases we want this to be auto.
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long character_id;
	
	private long user_id;
	
	private String name;
	
	private long weapon_id;
	
	private long armor_id;
	
	private int gold;
	
	private int health;

	public CharacterSheet() {
		super();
		this.weapon_id = 1;
		this.armor_id = 1;
		this.health = 10;
	}

	public CharacterSheet(long character_id, long user_id, String name, long weapon_id, long armor_id, int gold,
			int health) {
		super();
		this.character_id = character_id;
		this.user_id = user_id;
		this.name = name;
		this.weapon_id = weapon_id;
		this.armor_id = armor_id;
		this.gold = gold;
		this.health = health;
	}

	public long getCharacter_id() {
		return character_id;
	}

	public void setCharacter_id(long character_id) {
		this.character_id = character_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getWeapon_id() {
		return weapon_id;
	}

	public void setWeapon_id(long weapon_id) {
		this.weapon_id = weapon_id;
	}

	public long getArmor_id() {
		return armor_id;
	}

	public void setArmor_id(long armor_id) {
		this.armor_id = armor_id;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public int hashCode() {
		return Objects.hash(armor_id, character_id, gold, health, name, user_id, weapon_id);
	}

	@Override
	public String toString() {
		return "CharacterSheet [character_id=" + character_id + ", user_id=" + user_id + ", name=" + name
				+ ", weapon_id=" + weapon_id + ", armor_id=" + armor_id + ", gold=" + gold + ", health=" + health + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterSheet other = (CharacterSheet) obj;
		return armor_id == other.armor_id && character_id == other.character_id && gold == other.gold
				&& health == other.health && Objects.equals(name, other.name) && user_id == other.user_id
				&& weapon_id == other.weapon_id;
	}
	
	

}

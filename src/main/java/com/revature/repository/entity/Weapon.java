package com.revature.repository.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "weapons")
public class Weapon {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long weapon_id;
	
	private String name;
	
	private  int strength;
	
	private int cost;
	
	public Weapon() {
		super();
	}

	public Weapon(long weapon_id, String name, int strength, int cost) {
		super();
		this.weapon_id = weapon_id;
		this.name = name;
		this.strength = strength;
		this.cost = cost;
	}

	public long getWeapon_id() {
		return weapon_id;
	}

	public void setWeapon_id(long weapon_id) {
		this.weapon_id = weapon_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, name, strength, weapon_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Weapon other = (Weapon) obj;
		return cost == other.cost && Objects.equals(name, other.name) && strength == other.strength
				&& weapon_id == other.weapon_id;
	}

	@Override
	public String toString() {
		return "Weapon [weapon_id=" + weapon_id + ", name=" + name + ", strength=" + strength + ", cost=" + cost + "]";
	}
	
}

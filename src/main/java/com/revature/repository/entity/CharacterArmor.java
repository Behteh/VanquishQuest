package com.revature.repository.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "character_armor")
public class CharacterArmor {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "character_id")
	private long characterId;
	
	@Column(name = "armor_id")
	private long armorId;

	public CharacterArmor() {
		super();
	}

	public CharacterArmor(long id, long characterId, long armorId) {
		this.id = id;
		this.characterId = characterId;
		this.armorId = armorId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}

	public long getArmorId() {
		return armorId;
	}

	public void setArmorId(long armorId) {
		this.armorId = armorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(armorId, characterId, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterArmor other = (CharacterArmor) obj;
		return armorId == other.armorId && characterId == other.characterId && id == other.id;
	}

	@Override
	public String toString() {
		return "CharacterArmor [id=" + id + ", characterId=" + characterId + ", armorId=" + armorId + "]";
	}
	
}

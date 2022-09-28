package com.revature.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.CharacterNotFoundException;
import com.revature.exceptions.GameUserAlreadyExistsException;
import com.revature.exceptions.MessageNotFoundException;
import com.revature.exceptions.NoArmorsException;
import com.revature.exceptions.NoPermissionException;
import com.revature.exceptions.NoWeaponsException;
import com.revature.repository.entity.Armor;
import com.revature.repository.entity.CharacterSheet;
import com.revature.repository.entity.PrivateMessage;
import com.revature.repository.entity.Weapon;
import com.revature.service.CharacterArmorService;
import com.revature.service.CharacterSheetService;
import com.revature.service.CharacterWeaponsService;
import com.revature.service.PrivateMessageService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/character")
public class CharacterController {
	
	private CharacterSheetService characterSheetService;
	private CharacterWeaponsService characterWeaponsService;
	private PrivateMessageService privateMessageService;
	private CharacterArmorService characterArmorService;

	public CharacterController(CharacterSheetService characterSheetService,
			CharacterWeaponsService characterWeaponsService, PrivateMessageService privateMessageService,
			CharacterArmorService characterArmorService) {
		this.characterSheetService = characterSheetService;
		this.characterWeaponsService = characterWeaponsService;
		this.privateMessageService = privateMessageService;
		this.characterArmorService = characterArmorService;
	}

	@PostMapping(value="/create")
	public ResponseEntity<?> create(
			@RequestParam(name="name", required=true, defaultValue="") String charname,
			@RequestParam(name="user_id", required=true) long user_id
			) throws GameUserAlreadyExistsException {
		if(characterSheetService.exists(charname))
		{
			throw new GameUserAlreadyExistsException();
		}
		CharacterSheet characterSheet = new CharacterSheet();
		characterSheet.setName(charname);
		characterSheet.setUser_id(user_id);
		CharacterSheet newCharacter = characterSheetService.save(characterSheet);
		return ResponseEntity.status(201).body(newCharacter);
	}

	@GetMapping(value="/{id}", produces="application/json")
	public @ResponseBody ResponseEntity<?> getCharacter(
			@PathVariable("id") long player_id
			) throws CharacterNotFoundException, NoArmorsException, NoWeaponsException {
		Optional<CharacterSheet> characterSheet = characterSheetService.findById(player_id);
		if(characterSheet.isPresent())
		{
			JSONObject json = new JSONObject();
			json.appendField("character_id", characterSheet.get().getCharacter_id());
			json.appendField("name", characterSheet.get().getName());
			json.appendField("gold", characterSheet.get().getGold());
			json.appendField("weapon_id", characterSheet.get().getWeapon_id());
			json.appendField("weapon_url", "/weapon/" + characterSheet.get().getWeapon_id());
			json.appendField("armor_id", characterSheet.get().getArmor_id());
			json.appendField("armor_url", "/armor/" + characterSheet.get().getArmor_id());
			json.appendField("user_id", characterSheet.get().getUser_id());
			return ResponseEntity.ok(json);
		}
		throw new CharacterNotFoundException("The character was not found");
	}
	
	@PutMapping(value="/{id}/update")
	public ResponseEntity<?> updateUser(
			@PathVariable("id") long player_id,
			@RequestParam(name="user_id", required=true, defaultValue = "0") long user_id,
			@RequestParam(name="weapon_id", required=false, defaultValue = "0") long weapon_id,
			@RequestParam(name="armor_id", required=false, defaultValue = "0") long armor_id,
			@RequestParam(name="name", required=false, defaultValue = "0") String name,
			@RequestParam(name = "gold", required=false, defaultValue = "0") int gold,
			@RequestParam(name = "health", required=false, defaultValue = "0") int health
			) throws CharacterNotFoundException, GameUserAlreadyExistsException {
		if(!characterSheetService.exists(player_id))
		{
			throw new CharacterNotFoundException("The character was not found");
		}
		CharacterSheet characterSheet = new CharacterSheet();
		characterSheet.setCharacter_id(player_id);
		characterSheet.setUser_id(user_id);
		if(weapon_id != 0)
		{
			characterSheet.setWeapon_id(weapon_id);
		}
		if(armor_id != 0)
		{
			characterSheet.setArmor_id(armor_id);
		}
		if(!name.equals("0"))
		{
			if(characterSheetService.exists(name))
			{
				throw new GameUserAlreadyExistsException();
			}
			characterSheet.setName(name);
		}
		if(gold != 0)
		{
			characterSheet.setGold(gold);
		}
		if(health != 0)
		{
			characterSheet.setHealth(health);
		}
		return ResponseEntity.ok(characterSheetService.save(characterSheet));
	}
	
	@DeleteMapping(value="/{id}/delete")
	public @ResponseBody ResponseEntity<?> deleteChar(
			@PathVariable("id") long player_id
			) throws CharacterNotFoundException {
		if(characterSheetService.exists(player_id))
		{
			characterSheetService.deleteById(player_id);
			return ResponseEntity.status(204).body("");
		}
		throw new CharacterNotFoundException("The character was not found");
	}
	
	@GetMapping(value="/{id}/weapons", produces="application/json")
	public @ResponseBody ResponseEntity<?> getWeapons(
			@PathVariable("id") long player_id
			) throws CharacterNotFoundException, NoWeaponsException{

		if(!characterSheetService.exists(player_id))
		{
			throw new CharacterNotFoundException();
		}
		ArrayList<Weapon> weapons = characterWeaponsService.findWeaponsByCharacterId(player_id);
		if(weapons.size() == 0)
		{
			throw new NoWeaponsException();
		}
		return ResponseEntity.ok(weapons);
	}
	
	@GetMapping(value="/{id}/armors", produces="application/json")
	public @ResponseBody ResponseEntity<?> getArmor(
			@PathVariable("id") long player_id
			) throws CharacterNotFoundException, NoArmorsException {
		if(!characterSheetService.exists(player_id))
		{
			throw new CharacterNotFoundException();
		}
		ArrayList<Armor> armors = characterArmorService.findArmorsByCharacterId(player_id);
		if(armors.size() == 0)
		{
			throw new NoArmorsException();
		}
		return ResponseEntity.ok(armors);
	}
	
	@PutMapping(value="/{id}/message")
	public ResponseEntity<PrivateMessage> postMessage(
			@PathVariable("id") long player_id,
			@RequestParam(name="id", required=true) long char_id,
			@RequestParam(name="topic", required=true) String topic,
			@RequestParam(name="message", required=true) String message
			) throws CharacterNotFoundException {
		if(characterSheetService.exists(player_id))
		{
			PrivateMessage pm = new PrivateMessage();
			pm.setFromUserId(char_id);
			pm.setToUserId(player_id);
			pm.setTopic(topic);
			pm.setMessage(message);
			pm.setTimestamp(LocalDateTime.now());
			return ResponseEntity.status(201).body(privateMessageService.save(pm));
		}
		throw new CharacterNotFoundException();
	}
	
	@GetMapping(value="/{id}/messages", produces="application/json")
	public @ResponseBody ResponseEntity<List<PrivateMessage>> getMessages(
			@PathVariable("id") long player_id
			) throws CharacterNotFoundException, MessageNotFoundException {
	
		if(characterSheetService.exists(player_id))
		{
			Optional<List<PrivateMessage>> messages = privateMessageService.getRecentMessages(player_id);
			if(messages.isPresent())
			{
				if(messages.get().size() > 0)
				{
					return ResponseEntity.ok(messages.get());
				}
				return ResponseEntity.status(204).body(messages.get());
			}
			throw new MessageNotFoundException();
		}
		else
		{
			throw new CharacterNotFoundException();
		}
		
	}
	
	@GetMapping(value="/{id}/message/{mid}", produces="application/json")
	public @ResponseBody ResponseEntity<?> getMessage(
			@PathVariable("id") long player_id,
			@PathVariable("mid") long message_id
			) throws CharacterNotFoundException, MessageNotFoundException, NoPermissionException {
		Optional<PrivateMessage> pm =  privateMessageService.findById(message_id);
		if(!pm.isPresent())
		{
			throw new MessageNotFoundException();
		}
		if(pm.get().getToUserId() != player_id)
		{
			throw new NoPermissionException();
		}
		return ResponseEntity.ok(pm.get());
	}
	
	@GetMapping(value="/{id}/message/search", produces="application/json")
	public @ResponseBody ResponseEntity<?> findMessage(
			@PathVariable("id") long player_id,
			@RequestParam(name="keywords", required=true) String keywords
			) throws CharacterNotFoundException, MessageNotFoundException {
		if(!characterSheetService.exists(player_id))
		{
			throw new CharacterNotFoundException();
		}
		Optional<List<PrivateMessage>> messages = privateMessageService.searchMessages(player_id, keywords);
		if(messages.isPresent())
		{
			if(messages.get().size() == 0)
			{
				return ResponseEntity.status(204).body(messages.get());
			}
			return ResponseEntity.ok(messages.get());
		}
		throw new MessageNotFoundException();
	}
	
	@ExceptionHandler(CharacterNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object onCharacterNotFoundException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 404);
		jsonObject.appendField("error_message", "The character does not exist.");
		jsonObject.appendField("error_cause", "You entered an invalid character.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(MessageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object onMessageNotFoundException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 404);
		jsonObject.appendField("error_message", "The message does not exist.");
		jsonObject.appendField("error_cause", "You entered an invalid message id instead of following links.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(NoPermissionException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Object onNoPermissionException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 403);
		jsonObject.appendField("error_message", "You do not have permission to access this content.");
		jsonObject.appendField("error_cause", "You are not logged in or are trying to access a resource you are not allowed to access.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(NoWeaponsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object onNoWeaponsException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 400);
		jsonObject.appendField("error_message", "The character does not have any weapons.");
		jsonObject.appendField("error_cause", "You visited this page directly rather than using a link.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(NoArmorsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object onNoArmorsException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 400);
		jsonObject.appendField("error_message", "The character does not have any armors.");
		jsonObject.appendField("error_cause", "You visited this page directly rather than using a link.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(GameUserAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object onGameUserAlreadyExistsException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 400);
		jsonObject.appendField("error_message", "The character name is already in use.");
		jsonObject.appendField("error_cause", "You need to use a different name for your character.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
}
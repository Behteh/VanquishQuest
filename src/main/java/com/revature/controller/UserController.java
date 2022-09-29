package com.revature.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.GameUserAlreadyExistsException;
import com.revature.exceptions.GameUserNotFoundException;
import com.revature.repository.entity.GameUser;
import com.revature.service.GameUserService;

import net.minidev.json.JSONObject;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*")
public class UserController {

	private GameUserService gameUserService;
	@Autowired
	public UserController(GameUserService gameUserService) {
		this.gameUserService = gameUserService;
	}

	@GetMapping(value="/{id}", produces="application/json")
	public @ResponseBody GameUser getUser(
			@PathVariable("id") long user_id)
			throws GameUserNotFoundException{
		Optional<?> gameUser = gameUserService.findById(user_id);
		if(gameUser.isPresent()) {
			return (GameUser) gameUser.get();
		}
		throw new GameUserNotFoundException("User not found.");
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<?> register(
			@RequestParam(name="username", required=true, defaultValue="") String username,
			@RequestParam(name="password", required=true, defaultValue="") String password,
			Model model) throws GameUserAlreadyExistsException {
			GameUser gameUser = gameUserService.register(username, password);
			if(gameUser.getUser_id() >= 1)
			{
				JSONObject obj = new JSONObject();
				obj.appendField("user_id", gameUser.getUser_id());
				obj.appendField("username", gameUser.getUsername());
				obj.appendField("user_title", gameUser.getUser_title());
				obj.appendField("avatar_url", gameUser.getAvatar_url());
				obj.appendField("admin", gameUser.isAdmin());
				obj.appendField("url", "/user/" + gameUser.getUser_id());
				return ResponseEntity.status(201).body(obj);
			}
			throw new GameUserAlreadyExistsException("Username already exists");
	}

	@PostMapping(
			value="/login",
			produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	public ResponseEntity<?> login(
			@RequestParam(name="username", required=true, defaultValue="") String username,
			@RequestParam(name="password", required=true, defaultValue="") String password,
			Model model) throws GameUserNotFoundException {
		
		Optional<GameUser> gameUser = gameUserService.findByUsernameAndPassword(username, password);
		if(gameUser.isPresent())
		{
			JSONObject obj = new JSONObject();
			obj.appendField("user_id", gameUser.get().getUser_id());
			obj.appendField("username", gameUser.get().getUsername());
			obj.appendField("user_title", gameUser.get().getUser_title());
			obj.appendField("avatar_url", gameUser.get().getAvatar_url());
			obj.appendField("admin", gameUser.get().isAdmin());
			obj.appendField("url", "/user/" + gameUser.get().getUser_id());
			return ResponseEntity.ok().body(obj);
		}
		throw new GameUserNotFoundException("User not found");
	}
	
	@ExceptionHandler(GameUserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object onGameUserNotFoundException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 404);
		jsonObject.appendField("error_message", "The user does not exist.");
		jsonObject.appendField("error_cause", "You entered an invalid user id or the user credentials are not correct.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(GameUserAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object onGameUserAlreadyExistsException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 400);
		jsonObject.appendField("error_message", "The username is already taken.");
		jsonObject.appendField("error_cause", "You have entered in a username that is already in use. Either change your username or login with that username.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
}

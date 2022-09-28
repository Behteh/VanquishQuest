package com.revature.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.revature.exceptions.DuplicateGameUserException;
import com.revature.exceptions.GameUserNotFoundException;
import com.revature.exceptions.NoFriendsException;
import com.revature.repository.entity.Friend;
import com.revature.repository.entity.FriendRequest;
import com.revature.service.FriendRequestService;
import com.revature.service.FriendService;
import com.revature.service.GameUserService;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("/friends")
public class FriendsController {

	private FriendService friendsService;
	private FriendRequestService frs;
	private GameUserService gus;
	
	

	public FriendsController(FriendService friendsService, FriendRequestService frs, GameUserService gus) {
		this.friendsService = friendsService;
		this.frs = frs;
		this.gus = gus;
	}
	@GetMapping(value="/{id}", produces="application/json")
	public @ResponseBody ResponseEntity<?> getFriends( //Change to return Friends array once implemented
			@PathVariable("id") long user_id
			) throws GameUserNotFoundException, NoFriendsException{
		if(!gus.exists(user_id))
		{
			throw new GameUserNotFoundException();
		}
		List<Friend> friends = friendsService.findAllFriendsByUser1(user_id);
		if(friends.size() > 0)
		{
			return ResponseEntity.ok(friends);
		}
		return ResponseEntity.status(204).body(friends);
	}
	@PutMapping(value="/{id}/add")
	public ResponseEntity<?> addFriend(
			@PathVariable("id") long user_id,
			@RequestParam(name="id", required=true) long id
			) throws GameUserNotFoundException, DuplicateGameUserException{
		
		Friend friend = new Friend();
		if(!gus.exists(id) || !gus.exists(user_id))
		{
			throw new GameUserNotFoundException();
		}
		if(id == user_id)
		{
			throw new DuplicateGameUserException();
		}
		friend.setUser1(user_id);
		friend.setUser2(id);
		return ResponseEntity.status(201).body(friendsService.save(friend));
	}
	@DeleteMapping(value="/{id}/delete")
	public ResponseEntity<?> deleteFriend(
			@PathVariable("id") long id
			) throws GameUserNotFoundException{
		if(friendsService.exists(id))
		{
			friendsService.deleteById(id);
			return ResponseEntity.status(204).body("");
		}
		throw new GameUserNotFoundException();
	}
	
	@GetMapping(value = "/{id}/requests/view")
	public ResponseEntity<?> getFriendRequests(
			@PathVariable("id") long id) throws GameUserNotFoundException
	{
		if(!gus.exists(id))
		{
			throw new GameUserNotFoundException();
		}
		ArrayList<FriendRequest> friends = frs.findAllById(id);
		if(friends.size() > 0)
		{
			return ResponseEntity.ok(friends);
		}
		return ResponseEntity.status(204).body(friends);
	}
	
	@PutMapping(value = "/{id}/requests/add")
	public ResponseEntity<?> addFriendRequest(
			@PathVariable("id") long id,
			@RequestParam(name = "id", required = true) long user_id) throws GameUserNotFoundException, DuplicateGameUserException
	{
		if(!gus.exists(user_id) || !gus.exists(id))
		{
			throw new GameUserNotFoundException();
		}
		if(id == user_id)
		{
			throw new DuplicateGameUserException();
		}
		
		FriendRequest fr = new FriendRequest();
		fr.setReceiver_id(id);
		fr.setSender_id(user_id);
		return ResponseEntity.status(201).body(frs.save(fr));
	}
	
	@DeleteMapping(value = "/{id}/requests/delete")
	public ResponseEntity<?> deleteFriendRequest(
			@PathVariable("id") long id,
			@RequestParam(name = "id", required=true) long request_id) throws GameUserNotFoundException
	{
		if(!gus.exists(id))
		{
			throw new GameUserNotFoundException();
		}
		frs.deleteById(request_id);
		return ResponseEntity.status(204).body("");
	}

	
	@ExceptionHandler(GameUserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object onGameUserAlreadyExistsException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 404);
		jsonObject.appendField("error_message", "The user does not exist.");
		jsonObject.appendField("error_cause", "You navigated directly rather than using a link.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(NoFriendsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object onNoFriendsException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 404);
		jsonObject.appendField("error_message", "The user does not have any friends.");
		jsonObject.appendField("error_cause", "The user needs to add friends.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
	@ExceptionHandler(DuplicateGameUserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Object onDuplicateGameUserException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 400);
		jsonObject.appendField("error_message", "The source user and destination user are the same.");
		jsonObject.appendField("error_cause", "You used the same user for the source and destination.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
	
}

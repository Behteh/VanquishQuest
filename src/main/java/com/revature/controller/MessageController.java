package com.revature.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

import com.revature.exceptions.GameUserNotFoundException;
import com.revature.exceptions.MessageNotFoundException;
import com.revature.repository.entity.ChatMessage;
import com.revature.service.ChatMessageService;

import net.minidev.json.JSONObject;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	private ChatMessageService chatMessageService;

	public MessageController(ChatMessageService chatMessageService) {
		this.chatMessageService = chatMessageService;
	}



	@GetMapping(value="/view", produces="application/json")
	public @ResponseBody ResponseEntity<?> getMessages(
			@RequestParam(name="page", required=false) Integer page
			) throws MessageNotFoundException {
		Optional<List<ChatMessage>> messages = chatMessageService.getRecentMessages();
		if(messages.isPresent())
		{
			return ResponseEntity.ok(messages.get());
		}
		throw new MessageNotFoundException("There are no messages");
	}
	
	@PutMapping(value="/add", produces="application/json")
	public @ResponseBody ResponseEntity<?> createMessage(
			@RequestParam(name = "id", required=true) long character_id,
			@RequestParam(name = "message", required=true) String message)
	{
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setCharacter_id(character_id);
		chatMessage.setMessage(message);
		return ResponseEntity.status(201).body(chatMessageService.save(chatMessage));
	}
	
	@DeleteMapping(value="/{id}/delete")
	public @ResponseBody ResponseEntity<?> deleteMessage(
			@PathVariable("id") long id) throws MessageNotFoundException
	{
		Optional<ChatMessage> message = chatMessageService.findById(id);
		if(message.isPresent())
		{
			chatMessageService.deleteById(id);
			return ResponseEntity.status(204).body("");
		}
		throw new MessageNotFoundException();
	}
	
	@GetMapping(value="/search", produces="application/json")
	public @ResponseBody ResponseEntity<?> searchMessages(
			@RequestParam(name="keywords", required=true) String keywords) throws MessageNotFoundException
	{
		Optional<List<ChatMessage>> messages = chatMessageService.searchMessages(keywords);
		if(messages.isPresent())
		{
			return ResponseEntity.ok(messages);
		}
		throw new MessageNotFoundException();
	}
	
	@ExceptionHandler(MessageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object onMessageNotFoundException() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.appendField("error_code", 404);
		jsonObject.appendField("error_message", "The message does not exist.");
		jsonObject.appendField("error_cause", "You navigated directly rather than using a link.");
		jsonObject.appendField("date", LocalDate.now());
		jsonObject.appendField("time", LocalTime.now());
		return jsonObject;
	}
}

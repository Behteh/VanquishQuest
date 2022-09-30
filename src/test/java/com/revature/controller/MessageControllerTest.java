package com.revature.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageControllerTest {

	private static int messageId = 0;
    @Autowired
    private MockMvc mvc;

    @Test
    public void getMessagesTest() throws Exception{
    		String result = mvc.perform(MockMvcRequestBuilders.get("/message/view"))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    		assert(result.contains("{\"message_id\":11,\"character_id\":1,\"message\":\"Ah, General Kenobi\",\"timestamp\":\"2022-09-30T02:29:04.362\"}"));
    }

	@Test
    @Order(1)
	public void addMessageTest() throws Exception{
		String result = mvc.perform(MockMvcRequestBuilders.put("/message/add").param("id", "2").param("message", "testMessage"))
			.andExpect(status().is(201))
			.andReturn().getResponse().getContentAsString();
		
		String[] resultSplit = result.split("[:,]");
    	System.out.println(resultSplit[1]);
    	messageId = Integer.parseInt(resultSplit[1]);
	}

	@Test
	@Order(2)
	public void deleteMessageTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/message/"+messageId+"/delete"))
			.andExpect(status().is(204));
	}
	
	@Test
	@Order(3)
	public void deleteMessageNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/message/"+messageId+"/delete"))
			.andExpect(status().is(404));
	}
}

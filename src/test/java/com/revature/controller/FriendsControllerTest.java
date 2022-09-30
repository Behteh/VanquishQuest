package com.revature.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FriendsControllerTest {

	static int friendId = 0;
    @Autowired
    private MockMvc mvc;
    
    @Test
    public void getFriendsTest() throws Exception{
    		mvc.perform(MockMvcRequestBuilders.get("/friends/1"))
    			.andExpect(status().isOk())
    			.andExpect(content().string(equalTo("[{\"friend_id\":1,\"user1\":1,\"user2\":2},{\"friend_id\":2,\"user1\":1,\"user2\":3}]")));
    }
    
    @Test
    public void getNoFriendsTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/friends/4"))
		.andExpect(status().is(204));
    }
    
    @Test
    public void getFriendsNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/friends/999999"))
		.andExpect(status().is(404));
    }

    @Test
    public void addFriendDuplicateTest() throws Exception{
    	mvc.perform(MockMvcRequestBuilders.put("/friends/3/add").param("id", "3"))
    	.andExpect(status().is(400));
    }

    @Test
    public void addFriendNotFoundTest() throws Exception{
    		mvc.perform(MockMvcRequestBuilders.put("/friends/3/add").param("id", "99999999"))
    		.andExpect(status().is(404));
    }
    
    @Test
    @Order(1)
    public void addFriendTest() throws Exception{
    	String result = mvc.perform(MockMvcRequestBuilders.put("/friends/3/add").param("id", "4"))
		.andExpect(status().is(201))
		.andReturn().getResponse().getContentAsString();
    	
    	String[] resultSplit = result.split("[:,]");
    	System.out.println(resultSplit[1]);
    	friendId = Integer.parseInt(resultSplit[1]);
    }
    
    @Test
    @Order(2)
    public void deleteFriendTest() throws Exception{
    	mvc.perform(MockMvcRequestBuilders.delete("/friends/"+friendId+"/delete"))
			.andExpect(status().is(204));
    }
    
    @Test
    @Order(3)
    public void deleteFriendNotFoundTest() throws Exception{
    		mvc.perform(MockMvcRequestBuilders.delete("/friends/"+friendId+"/delete"))
    		.andExpect(status().is(404));
    }
    
    @Test
    public void findRequestsTest() throws Exception{
    	mvc.perform(MockMvcRequestBuilders.get("/friends/1/requests/view"))
		.andExpect(content().string(equalTo("[{\"request_id\":3,\"sender_id\":3,\"receiver_id\":1},{\"request_id\":4,\"sender_id\":2,\"receiver_id\":1}]")));
    }
    
    @Test
    public void findNoRequestsTest() throws Exception{
    	mvc.perform(MockMvcRequestBuilders.get("/friends/4/requests/view"))
			.andExpect(status().is(204))
			.andExpect(content().string(equalTo("[]")));
    }
    
    @Test
    public void findRequestsNotFoundTest() throws Exception{
    	mvc.perform(MockMvcRequestBuilders.get("/friends/999999/requests/view"))
    	.andExpect(status().is(404));
    }

    @Test
    public void addFriendRequestDuplicateTest() throws Exception{
    	mvc.perform(MockMvcRequestBuilders.put("/friends/4/requests/add").param("id", "4"))
    	.andExpect(status().is(400));
    }

    @Test
    public void addFriendRequestNotFoundTest() throws Exception{
    	mvc.perform(MockMvcRequestBuilders.put("/friends/4/requests/add").param("id", "999999"))
    	.andExpect(status().is(404));
    }
    
    //I have to comment this out because it's not being deleted
    //This test works though
    @Test
    @Order(4)
    public void addFriendRequestTest() throws Exception{
    	String result = mvc.perform(MockMvcRequestBuilders.put("/friends/4/requests/add").param("id", "3"))
			.andExpect(status().is(201))
			.andReturn().getResponse().getContentAsString();
    	
    	String[] resultSplit = result.split("[:,]");
    	friendId = Integer.parseInt(resultSplit[1]);
    }

    @Test
    @Order(5)
    public void deleteFriendRequestTest() throws Exception{
    	System.out.println("Request ID is "+friendId);
    	mvc.perform(MockMvcRequestBuilders.delete("/friends/4/requests/delete").param("id", String.valueOf(friendId)))
			.andExpect(status().is(204));
    }

    @Test
    @Order(6)
    public void deleteFriendRequestNotFoundTest() throws Exception{
    	Assertions.assertThrows(NestedServletException.class, ()->
    		mvc.perform(MockMvcRequestBuilders.delete("/friends/4/requests/delete").param("id", String.valueOf(friendId))));
    }
    
}
package com.revature.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUserTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/user/1"))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("{\"user_id\":1,\"username\":\"markj\",\"password\":\"pass123\",\"admin\":false,\"user_title\":null,\"avatar_url\":null}")));
    }

    @Test
    public void getUserNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/user/999999999"))
			.andExpect(status().is(404));
    }
    
    //I can't test register because I can't delete the user I create

    @Test
    public void userLoginTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.post("/user/login").param("username", "brandont").param("password", "pass123"))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("{\"avatar_url\":null,\"user_id\":4,\"user_title\":null,\"admin\":false,\"url\":\"/user/4\",\"username\":\"brandont\"}")));
    }

    @Test
    public void userLoginNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.post("/user/login").param("username", "aaa").param("password", "bbb"))
			.andExpect(status().is(404));
    }
}
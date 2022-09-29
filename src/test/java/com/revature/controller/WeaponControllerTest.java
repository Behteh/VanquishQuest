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
public class WeaponControllerTest {

	static int createdWeaponId = 0;
	
    @Autowired
    private MockMvc mvc;
    

    @Test
    public void viewTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/weapon/view"))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("[{\"cost\":8,\"strength\":10,\"name\":\"Great sword\",\"url\":\"/weapon/1\",\"weapon_id\":1},{\"cost\":5,\"strength\":8,\"name\":\"Axe\",\"url\":\"/weapon/2\",\"weapon_id\":2},{\"cost\":4,\"strength\":6,\"name\":\"Mace\",\"url\":\"/weapon/3\",\"weapon_id\":3},{\"cost\":1,\"strength\":4,\"name\":\"Fish\",\"url\":\"/weapon/4\",\"weapon_id\":4},{\"cost\":999,\"strength\":100,\"name\":\"Sharp Teeth\",\"url\":\"/weapon/5\",\"weapon_id\":5},{\"cost\":4000,\"strength\":30,\"name\":\"Lightning Saber\",\"url\":\"/weapon/7\",\"weapon_id\":7}]")));
    }

    @Test
    public void viewOneNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/weapon/999999"))
		.andExpect(status().is(404));
    }
    
    @Test
    public void viewOneTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/weapon/1"))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("{\"cost\":8,\"strength\":10,\"name\":\"Great sword\",\"url\":\"/weapon/view\",\"weapon_id\":1}")));
    }

    @Test
    public void createDuplicateTest() throws Exception{
    	Assertions.assertThrows(NestedServletException.class, ()->
    		mvc.perform(MockMvcRequestBuilders.put("/weapon/create")
    		.param("name", "Axe").param("strength", "99").param("cost", "88")));
    	//Name Plate already exists, so trying to make it again will throw an error
    }
    
    @Test
    @Order(1)
    public void createTest() throws Exception{
		String newWeapon = mvc.perform(MockMvcRequestBuilders.put("/weapon/create")
			.param("name", "testWeapon").param("strength", "99").param("cost", "88"))
			.andExpect(status().is(201))
            .andReturn().getResponse().getContentAsString();

        String[] resultSplit = newWeapon.split("[:,]");
        createdWeaponId = Integer.parseInt(resultSplit[1]);
    }
    
    @Test
    @Order(2)
    public void readCreatedWeaponTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/weapon/"+createdWeaponId))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("{\"cost\":88,\"strength\":99,\"name\":\"testWeapon\",\"url\":\"/weapon/view\",\"weapon_id\":"+createdWeaponId+"}")));
    }

    @Test
    @Order(3)
    public void updateCreatedWeaponDuplicateTest() throws Exception{
    	Assertions.assertThrows(NestedServletException.class, ()->
		mvc.perform(MockMvcRequestBuilders.put("/weapon/"+createdWeaponId+"/update")
			.param("name", "Axe").param("strength", "77").param("cost", "66")));
    }
    
    @Test
    @Order(4)
    public void updateCreatedWeaponTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.put("/weapon/"+createdWeaponId+"/update")
			.param("name", "test2").param("strength", "77").param("cost", "66"))
			.andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void readUpdatedWeaponTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/weapon/"+createdWeaponId))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("{\"cost\":66,\"strength\":77,\"name\":\"test2\",\"url\":\"/weapon/view\",\"weapon_id\":"+createdWeaponId+"}")));
    }

    @Test
    @Order(6)
    public void deleteWeaponTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/weapon/"+createdWeaponId+"/delete"))
		.andExpect(status().is(204));
    }
    
    @Test
    @Order(7)
    public void deleteWeaponNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/weapon/"+createdWeaponId+"/delete"))
		.andExpect(status().is(404));
    }
}

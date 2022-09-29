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
public class ArmorControllerTest {

	static int createdArmorId = 0;
	
    @Autowired
    private MockMvc mvc;
    

    @Test
    public void viewTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/armor/view"))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("[{\"armor_id\":1,\"name\":\"Plate\",\"defense\":10,\"cost\":8},{\"armor_id\":2,\"name\":\"Mail\",\"defense\":8,\"cost\":5},{\"armor_id\":3,\"name\":\"Leather\",\"defense\":6,\"cost\":4},{\"armor_id\":4,\"name\":\"Trousers\",\"defense\":4,\"cost\":1},{\"armor_id\":5,\"name\":\"Fluffy Rabbit Fur\",\"defense\":100,\"cost\":999},{\"armor_id\":6,\"name\":\"Lighting Cape\",\"defense\":30,\"cost\":7030},{\"armor_id\":8,\"name\":\"Fire Cape\",\"defense\":30,\"cost\":7030}]")));
    }

    @Test
    public void viewOneNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/armor/999999"))
		.andExpect(status().is(404));
    }
    
    @Test
    public void viewOneTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/armor/1"))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("{\"armor_id\":1,\"name\":\"Plate\",\"defense\":10,\"cost\":8}")));
    }
    
    @Test
    public void createDuplicateTest() throws Exception{
    	Assertions.assertThrows(NestedServletException.class, ()->
    		mvc.perform(MockMvcRequestBuilders.put("/armor/create")
    		.param("name", "Plate").param("defense", "99").param("cost", "88")));
    	//Name Plate already exists, so trying to make it again will throw an error
    }
    
    @Test
    @Order(1)
    public void createTest() throws Exception{
		String newArmor = mvc.perform(MockMvcRequestBuilders.put("/armor/create")
			.param("name", "testArmor").param("defense", "99").param("cost", "88"))
			.andExpect(status().is(201))
            .andReturn().getResponse().getContentAsString();

        String[] resultSplit = newArmor.split("[:,]");
        createdArmorId = Integer.parseInt(resultSplit[1]);
    }
    
    @Test
    @Order(2)
    public void readCreatedArmorTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/armor/"+createdArmorId))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("{\"armor_id\":"+createdArmorId+",\"name\":\"testArmor\",\"defense\":99,\"cost\":88}")));
    }
    
    @Test
    @Order(3)
    public void updateCreatedArmorDuplicateTest() throws Exception{
    	Assertions.assertThrows(NestedServletException.class, ()->
		mvc.perform(MockMvcRequestBuilders.put("/armor/"+createdArmorId+"/update")
			.param("name", "Plate").param("defense", "77").param("cost", "66")));
    }
    
    @Test
    @Order(4)
    public void updateCreatedArmorTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.put("/armor/"+createdArmorId+"/update")
			.param("name", "test2").param("defense", "77").param("cost", "66"))
			.andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void readUpdatedArmorTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/armor/"+createdArmorId))
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("{\"armor_id\":"+createdArmorId+",\"name\":\"test2\",\"defense\":77,\"cost\":66}")));
    }
    
    @Test
    @Order(6)
    public void deleteArmorTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/armor/"+createdArmorId+"/delete"))
		.andExpect(status().is(204));
    }
    
    @Test
    @Order(7)
    public void deleteArmorNotFoundTest() throws Exception{
		mvc.perform(MockMvcRequestBuilders.delete("/armor/"+createdArmorId+"/delete"))
		.andExpect(status().is(404));
    }
}

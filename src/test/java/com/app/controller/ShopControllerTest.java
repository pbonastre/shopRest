package com.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {
	
	static final String INITIAL_LAT ="39.4729669";
	static final String INITIAL_LNG ="-0.3536548";
	

	@Autowired
	  private MockMvc mockMvc;
	
	@Test
	public void addTest() throws Exception {
		
	 String jsonMessage = "{ \"nameId\" : \"SHOPNAME\",\"shopAddress\" : {\"number\": \"1\",\"postCode\": \"46021\"	} }";
	 this.mockMvc.perform(post("/shops").content(jsonMessage).contentType("application/json")).andDo(print()).andExpect(status().isCreated());
		
	}
	
	@Test
	public void nearestShopTest() throws Exception {
		
		this.mockMvc.perform(get("/distance").param("latitude", INITIAL_LAT).param("longitude", INITIAL_LNG))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string("The nearest shop adress is 46021 Valencia, Spain, with Latitude 39.4729669 and Longitude -0.3536548"));	
	}

}
package com.wipro.capstone.controller.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.capstone.InventoryMicroservicesApplication;
import com.wipro.capstone.entity.Inventory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryMicroservicesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class InventoryControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	private List<Inventory> inventories;

	@BeforeEach
	public void setAllInventories() {
		inventories = new ArrayList<Inventory>();
		Inventory inventory = new Inventory(1, 1, 3);
		Inventory inventory2 = new Inventory(2, 2, 5);
		inventories.add(inventory);
		inventories.add(inventory2);
	}

	@AfterEach
	public void emptyInventories() {
		inventories.clear();
	}

	@Test
	@Order(1)
	void test_addInventory() throws Exception {

		ResultActions response = mockMvc.perform(post("/api/inventory").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(inventories.get(0))));

		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.inventoryid", is(inventories.get(0).getInventoryid())))
				.andExpect(jsonPath("$.productId", is(inventories.get(0).getProductId())))
				.andExpect(jsonPath("$.quantity", is(inventories.get(0).getQuantity())));

	}

	@Test
	@Order(2)
	void test_getInventory() throws Exception {
		//Failed case 
		this.mockMvc.perform(get("/api/inventory/7")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Inventory with id : 7 not Found")));
		//Pass case
		this.mockMvc.perform(get("/api/inventory/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.inventoryid", is(inventories.get(0).getInventoryid())))
				.andExpect(jsonPath("$.productId", is(inventories.get(0).getProductId())))
				.andExpect(jsonPath("$.quantity", is(inventories.get(0).getQuantity())));
	}

	@Test
	@Order(3)
	void test_getAllInventories() throws Exception {
		// testing for passed scenario
		this.mockMvc.perform(get("/api/inventory")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].inventoryid", is(inventories.get(0).getInventoryid())))
				.andExpect(jsonPath("$[0].productId", is(inventories.get(0).getProductId())))
				.andExpect(jsonPath("$[0].quantity", is(inventories.get(0).getQuantity())));

	}
	
	@Test
	@Order(4)
	void test_updateInventory() throws Exception {
		//Failed case 
		ResultActions responseFail = mockMvc.perform(put("/api/inventory/6").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(inventories.get(1))));

		responseFail.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Inventory with id : 6 not Found")));
		//Pass case 
		ResultActions response = mockMvc.perform(put("/api/inventory/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(inventories.get(1))));

		response.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.inventoryid", is(1)))
				.andExpect(jsonPath("$.productId", is(inventories.get(1).getProductId())))
				.andExpect(jsonPath("$.quantity", is(inventories.get(1).getQuantity())));

	}

	@Test
	@Order(5)
	void test_deleteInventory() throws Exception {
		//Failed case 
		this.mockMvc.perform(delete("/api/inventory/9")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Inventory with id : 9 not Found")));
		//Pass case 
		this.mockMvc.perform(delete("/api/inventory/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted Inventory with id : 1")));
	}

	@Test
	@Order(6)
	void test_deleteAllInventories() throws Exception {
		//Pass case 
		this.mockMvc.perform(delete("/api/inventory")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted All Inventories")));

		// get all inventories failed case
		this.mockMvc.perform(get("/api/inventory")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Inventories not Found")));

	}

}

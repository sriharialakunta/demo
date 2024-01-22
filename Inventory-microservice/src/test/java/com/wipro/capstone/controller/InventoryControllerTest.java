package com.wipro.capstone.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
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
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.capstone.entity.Inventory;
import com.wipro.capstone.service.InventoryService;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InventoryService service;

	private List<Inventory> inventories;

	@BeforeEach
	public void setAllInventories() {
		inventories = new ArrayList<Inventory>();
		Inventory inventory = new Inventory(1,1,3);
		Inventory inventory2 = new Inventory(2,2,5);
		inventories.add(inventory);
		inventories.add(inventory2);
	}
	@AfterEach
	public void emptyInventories() {
		inventories.clear();
	}

	@Test
	void test_getAllInventories() throws Exception {
		
		this.mockMvc.perform(get("/api/inventory")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("Inventories not Found")));
		
		when(service.findAllInventories()).thenReturn(inventories);
		
		this.mockMvc.perform(get("/api/inventory"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].inventoryid",is(inventories.get(0).getInventoryid())))
		.andExpect(jsonPath("$[0].productId",is(inventories.get(0).getProductId())))
		.andExpect(jsonPath("$[0].quantity",is(inventories.get(0).getQuantity())))
		.andExpect(jsonPath("$[1].inventoryid",is(inventories.get(1).getInventoryid())))
		.andExpect(jsonPath("$[1].productId",is(inventories.get(1).getProductId())))
		.andExpect(jsonPath("$[1].quantity",is(inventories.get(1).getQuantity())));

	}

	@Test
	void test_getInventory() throws Exception {
		
		this.mockMvc.perform(get("/api/inventory/1")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("Inventory with id : 1 not Found")));
		
		when(service.searchInventory(1)).thenReturn(Optional.of(inventories.get(0)));
		this.mockMvc.perform(get("/api/inventory/1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.inventoryid",is(inventories.get(0).getInventoryid())))
		.andExpect(jsonPath("$.productId",is(inventories.get(0).getProductId())))
		.andExpect(jsonPath("$.quantity",is(inventories.get(0).getQuantity()))); 

		
	}

	@Test
	void test_addInventory() throws Exception {
		
		when(service.addInventory(inventories.get(0))).thenReturn(inventories.get(0));
		
		ResultActions response=mockMvc.perform(post("/api/inventory")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(inventories.get(0))));
				
		response.andDo(print()).andExpect(status().isCreated())
		.andExpect(jsonPath("$.inventoryid",is(inventories.get(0).getInventoryid())))
		.andExpect(jsonPath("$.productId",is(inventories.get(0).getProductId())))
		.andExpect(jsonPath("$.quantity",is(inventories.get(0).getQuantity())));
		
		}
	
	@Test
	void test_updateInventory() throws Exception {
		
		when(service.searchInventory(1)).thenReturn(Optional.of(inventories.get(0)));
		when(service.addInventory(inventories.get(0))).thenReturn(inventories.get(0));
		
		ResultActions response=mockMvc.perform(put("/api/inventory/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(inventories.get(0))));
				
		response.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.inventoryid",is(inventories.get(0).getInventoryid())))
		.andExpect(jsonPath("$.productId",is(inventories.get(0).getProductId())))
		.andExpect(jsonPath("$.quantity",is(inventories.get(0).getQuantity())));
		
		}
	
	@Test
	void test_deleteInventory() throws Exception {
		
		this.mockMvc.perform(delete("/api/inventory/1")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("Inventory with id : 1 not Found")));

		when(service.searchInventory(1)).thenReturn(Optional.of(inventories.get(0)));
		when(service.deleteInventoryById(1)).thenReturn("Deleted Inventory with id : 1");
		this.mockMvc.perform(delete("/api/inventory/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted Inventory with id : 1")));


	}

	@Test
	void test_deleteAllInventories() throws Exception {
		when(service.deleteAllInventories()).thenReturn("Deleted All Inventories");
		this.mockMvc.perform(delete("/api/inventory")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted All Inventories")));

	}

}


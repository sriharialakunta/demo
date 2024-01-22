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
import com.wipro.capstone.entity.Cart;
import com.wipro.capstone.entity.LineItem;
import com.wipro.capstone.service.CartService;
import com.wipro.capstone.service.LineItemService;

@WebMvcTest(LineItemController.class)
class LineItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartService cartService;
	@MockBean
	private LineItemService service;

	private List<LineItem> items = new ArrayList<LineItem>();;

	@BeforeEach
	public void setAllLineItems() {

		LineItem item = new LineItem(1, 1, "boll", 4, 10.00, new Cart());
		LineItem item2 = new LineItem(2, 2, "hii", 2, 8.00, new Cart());
		items.add(item);
		items.add(item2);
	}

	@AfterEach
	public void emptyLineItems() {
		items.clear();
	}

	@Test
	void test_getAllLineItems() throws Exception {
		
		when(service.findAllLineItems()).thenReturn(items);
		ResultActions response=mockMvc.perform(get("/api/cart/lineitem")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(items)));
				
		response.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].itemId",is(items.get(0).getItemId())))
		.andExpect(jsonPath("$[0].productId",is(items.get(0).getProductId())))
		.andExpect(jsonPath("$[0].productName",is(items.get(0).getProductName())))
		.andExpect(jsonPath("$[0].quantity",is(items.get(0).getQuantity())))
		.andExpect(jsonPath("$[0].price",is(items.get(0).getPrice())))

		.andExpect(jsonPath("$[1].itemId",is(items.get(1).getItemId())))
		.andExpect(jsonPath("$[1].productId",is(items.get(1).getProductId())))
		.andExpect(jsonPath("$[1].productName",is(items.get(1).getProductName())))
		.andExpect(jsonPath("$[1].quantity",is(items.get(1).getQuantity())))
		.andExpect(jsonPath("$[1].price",is(items.get(1).getPrice())));
		

	}

	@Test
	void test_searchLineItem() throws Exception {
		
		when(service.searchLineItem(1)).thenReturn(Optional.of(items.get(0)));
		ResultActions response=mockMvc.perform(get("/api/cart/lineitem/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(items.get(0))));
				
		response.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.itemId",is(items.get(0).getItemId())))
		.andExpect(jsonPath("$.productId",is(items.get(0).getProductId())))
		.andExpect(jsonPath("$.productName",is(items.get(0).getProductName())))
		.andExpect(jsonPath("$.quantity",is(items.get(0).getQuantity())))
		.andExpect(jsonPath("$.price",is(items.get(0).getPrice())));
		
	}

	@Test
	void test_saveLineItem() throws Exception {
		
		when(service.addLineItem(items.get(0))).thenReturn(items.get(0));
		
		ResultActions response=mockMvc.perform(post("/api/cart/lineitem")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(items.get(0))));
				
		response.andDo(print()).andExpect(status().isCreated())
		.andExpect(jsonPath("$.itemId",is(items.get(0).getItemId())))
		.andExpect(jsonPath("$.productId",is(items.get(0).getProductId())))
		.andExpect(jsonPath("$.productName",is(items.get(0).getProductName())))
		.andExpect(jsonPath("$.quantity",is(items.get(0).getQuantity())))
		.andExpect(jsonPath("$.price",is(items.get(0).getPrice())));
		}

	@Test
	void test_updateLineItem() throws Exception {
		
		when(service.searchLineItem(1)).thenReturn(Optional.of(items.get(0)));
		when(service.addLineItem(items.get(0))).thenReturn(items.get(0));
		
		ResultActions response=mockMvc.perform(put("/api/cart/lineitem/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(items.get(0))));
				
		response.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.itemId",is(items.get(0).getItemId())))
		.andExpect(jsonPath("$.productId",is(items.get(0).getProductId())))
		.andExpect(jsonPath("$.productName",is(items.get(0).getProductName())))
		.andExpect(jsonPath("$.quantity",is(items.get(0).getQuantity())))
		.andExpect(jsonPath("$.price",is(items.get(0).getPrice())));
		
		}

	@Test
	void test_deleteLineItem() throws Exception {
	
		when(service.searchLineItem(1)).thenReturn(Optional.of(items.get(0)));
		when(service.deleteLineItemById(1)).thenReturn("Deleted lineitem with id : 1");
		this.mockMvc.perform(delete("/api/cart/lineitem/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted lineitem with id : 1")));


	}

	@Test
	void test_deleteLineItems() throws Exception {
		when(service.deleteAllLineItems()).thenReturn("Deleted All lineitems");
		this.mockMvc.perform(delete("/api/cart/lineitem")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted All lineitems")));

	}

}

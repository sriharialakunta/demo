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


@WebMvcTest(CartController.class)
class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartService service;
	@MockBean
	private LineItemService itemService;

	private List<Cart> carts = new  ArrayList<Cart>();
	
	@BeforeEach
	public void setAllCarts() {

		Cart inventory = new Cart(1, List.of(new LineItem(1,1,"boll",4,10.00,new Cart(1,null))));
		Cart inventory2 = new Cart(2, List.of(new LineItem(2,5,"bat",6,17.00,new Cart(2,null))));
		carts.add(inventory);
		carts.add(inventory2);
	}

	@AfterEach
	public void emptyCarts() {
		carts.clear();
	}

	@Test
	void test_findAllCarts() throws Exception {
		
		when(service.findAllCarts()).thenReturn(carts);

		this.mockMvc.perform(get("/api/cart"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].cartId",is(carts.get(0).getCartId())))
		.andExpect(jsonPath("$[1].cartId",is(carts.get(1).getCartId())));


	}

	@Test
	void test_searchCart() throws Exception {
		
		when(service.searchCart(1)).thenReturn(Optional.of(carts.get(0)));
		ResultActions response=mockMvc.perform(get("/api/cart/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(carts.get(0))));
				
		response.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void test_addCart() throws Exception {
		
		when(service.addCart(carts.get(0))).thenReturn(
				new Cart(1, List.of(new LineItem(1,1,"boll",4,10.00,new Cart(1,null))))
		);
		
		ResultActions response=mockMvc.perform(post("/api/cart/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(carts.get(0))));
				
		response.andDo(print()).andExpect(status().isCreated());
		
		}
	
	@Test
	void test_updateCart() throws Exception {
		
		when(service.searchCart(2)).thenReturn(Optional.of(carts.get(1)));
		when(service.addCart(carts.get(1))).thenReturn(carts.get(1));
		
		ResultActions response=mockMvc.perform(put("/api/cart/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(carts.get(1))));
				
		response.andDo(print()).andExpect(status().isOk())
		;
		
		}
	
	@Test
	void test_deleteCartById() throws Exception {
	
		when(service.searchCart(1)).thenReturn(Optional.of(carts.get(0)));
		when(service.deleteCartById(1)).thenReturn("Deleted cart with id : 1");
		this.mockMvc.perform(delete("/api/cart/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted cart with id : 1")));


	}

	@Test
	void test_deleteAllCarts() throws Exception {
		when(service.deleteAllCarts()).thenReturn("Deleted All Carts");
		this.mockMvc.perform(delete("/api/cart")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted All Carts")));

	}

}

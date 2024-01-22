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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.capstone.CartMicroserviceApplication;
import com.wipro.capstone.entity.Cart;
import com.wipro.capstone.entity.LineItem;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartMicroserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class CartControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

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
	@Order(3)
	void test_findAllCarts() throws Exception {
		
		this.mockMvc.perform(get("/api/cart"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].cartId",is(carts.get(0).getCartId())))
		.andExpect(jsonPath("$[0].lineItems").isArray())
		.andExpect(jsonPath("$[0].lineItems[0].itemId",is(carts.get(0).getLineItems().get(0).getItemId())))
		.andExpect(jsonPath("$[0].lineItems[0].productId",is(carts.get(0).getLineItems().get(0).getProductId())))
		.andExpect(jsonPath("$[0].lineItems[0].productName",is(carts.get(0).getLineItems().get(0).getProductName())))
		.andExpect(jsonPath("$[0].lineItems[0].quantity",is(carts.get(0).getLineItems().get(0).getQuantity())))
		.andExpect(jsonPath("$[0].lineItems[0].price",is(carts.get(0).getLineItems().get(0).getPrice())));
		


	}

	@Test
	@Order(2)
	void test_searchCart() throws Exception {
		
		this.mockMvc.perform(get("/api/cart/7")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Cart with id : 7 not Found")));
		
		ResultActions response=mockMvc.perform(get("/api/cart/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(carts.get(0))));
				
		response.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.cartId",is(carts.get(0).getCartId())))
		.andExpect(jsonPath("$.lineItems").isArray())
		.andExpect(jsonPath("$.lineItems[0].itemId",is(carts.get(0).getLineItems().get(0).getItemId())))
		.andExpect(jsonPath("$.lineItems[0].productId",is(carts.get(0).getLineItems().get(0).getProductId())))
		.andExpect(jsonPath("$.lineItems[0].productName",is(carts.get(0).getLineItems().get(0).getProductName())))
		.andExpect(jsonPath("$.lineItems[0].quantity",is(carts.get(0).getLineItems().get(0).getQuantity())))
		.andExpect(jsonPath("$.lineItems[0].price",is(carts.get(0).getLineItems().get(0).getPrice())));
		
	}

	@Test
	@Order(1)
	void test_addCart() throws Exception {
		
		ResultActions response=mockMvc.perform(post("/api/cart/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(carts.get(0))));
				
		response.andDo(print()).andExpect(status().isCreated())
		.andExpect(jsonPath("$.cartId",is(carts.get(0).getCartId())))
		.andExpect(jsonPath("$.lineItems").isArray())
		.andExpect(jsonPath("$.lineItems[0].itemId",is(carts.get(0).getLineItems().get(0).getItemId())))
		.andExpect(jsonPath("$.lineItems[0].productId",is(carts.get(0).getLineItems().get(0).getProductId())))
		.andExpect(jsonPath("$.lineItems[0].productName",is(carts.get(0).getLineItems().get(0).getProductName())))
		.andExpect(jsonPath("$.lineItems[0].quantity",is(carts.get(0).getLineItems().get(0).getQuantity())))
		.andExpect(jsonPath("$.lineItems[0].price",is(carts.get(0).getLineItems().get(0).getPrice())));
		
		}
	
	@Test
	@Order(4)
	void test_updateCart() throws Exception {
		
		ResultActions responseFail=mockMvc.perform(put("/api/cart/6")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(carts.get(1))));

		responseFail.andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(containsString("Cart with id : 6 not Found")));
		
		ResultActions response=mockMvc.perform(put("/api/cart/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(carts.get(1))));
				
		response.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.cartId",is(carts.get(0).getCartId())))
		.andExpect(jsonPath("$.lineItems").isArray())
		.andExpect(jsonPath("$.lineItems[1].itemId",is(carts.get(1).getLineItems().get(0).getItemId())))
		.andExpect(jsonPath("$.lineItems[1].productId",is(carts.get(1).getLineItems().get(0).getProductId())))
		.andExpect(jsonPath("$.lineItems[1].productName",is(carts.get(1).getLineItems().get(0).getProductName())))
		.andExpect(jsonPath("$.lineItems[1].quantity",is(carts.get(1).getLineItems().get(0).getQuantity())))
		.andExpect(jsonPath("$.lineItems[1].price",is(carts.get(1).getLineItems().get(0).getPrice())));
		
		}
	
	@Test
	@Order(5)
	void test_deleteCartById() throws Exception {
	
		this.mockMvc.perform(delete("/api/cart/7")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("Cart with id : 7 not Found")));
		
		this.mockMvc.perform(delete("/api/cart/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted cart with id : 1")));


	}

	@Test
	@Order(6)
	void test_deleteAllCarts() throws Exception {

		this.mockMvc.perform(delete("/api/cart")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted All Carts")));

		this.mockMvc.perform(get("/api/cart")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("Carts not Found")));

	}

}

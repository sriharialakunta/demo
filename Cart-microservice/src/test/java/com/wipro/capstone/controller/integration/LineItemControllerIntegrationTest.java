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
import com.wipro.capstone.repository.CartRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartMicroserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test2")
class LineItemControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CartRepository cartRepository;

	private List<LineItem> items = new ArrayList<LineItem>();;

	@BeforeEach
	public void setAllLineItems() {

		LineItem item = new LineItem(1, 1, "boll", 4, 10.00, new Cart(1,null));
		LineItem item2 = new LineItem(2, 2, "hii", 2, 8.00, new Cart(2,null));
		items.add(item);
		items.add(item2);
	}

	@AfterEach
	public void emptyLineItems() {
		items.clear();
	}

	@Test
	@Order(4)
	void test_getAllLineItems() throws Exception {
		
		ResultActions response=mockMvc.perform(get("/api/cart/lineitem")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(items)));
				
		response.andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].itemId",is(items.get(0).getItemId())))
		.andExpect(jsonPath("$[0].productId",is(items.get(0).getProductId())))
		.andExpect(jsonPath("$[0].productName",is(items.get(0).getProductName())))
		.andExpect(jsonPath("$[0].quantity",is(items.get(0).getQuantity())))
		.andExpect(jsonPath("$[0].price",is(items.get(0).getPrice())));
		

	}

	@Test
	@Order(3)
	void test_searchLineItem() throws Exception {
		
		this.mockMvc.perform(get("/api/cart/lineitem/7")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("LineItem with id : 7 not Found")));
		
		this.mockMvc.perform(get("/api/cart/lineitem/1")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.itemId",is(items.get(0).getItemId())))
		.andExpect(jsonPath("$.productId",is(items.get(0).getProductId())))
		.andExpect(jsonPath("$.productName",is(items.get(0).getProductName())))
		.andExpect(jsonPath("$.quantity",is(items.get(0).getQuantity())))
		.andExpect(jsonPath("$.price",is(items.get(0).getPrice())));
		
	}

	@Test
	@Order(1)
	void test_addLineItem() throws Exception {
		
		cartRepository.save(new Cart(1,null));
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
	@Order(2)
	void test_updateLineItem() throws Exception {
		
		ResultActions responseFailed=mockMvc.perform(put("/api/cart/lineitem/7")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(items.get(0))));
		responseFailed.andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("LineItem with id : 7 not Found")));
		
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
	@Order(5)
	void test_deleteLineItem() throws Exception {
		
		this.mockMvc.perform(delete("/api/cart/lineitem/7")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("LineItem with id : 7 not Found")));
	
		this.mockMvc.perform(delete("/api/cart/lineitem/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted lineitem with id : 1")));


	}

	@Test
	@Order(6)
	void test_deleteLineItems() throws Exception {

		this.mockMvc.perform(delete("/api/cart/lineitem")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted All lineitems")));
		
		this.mockMvc.perform(get("/api/cart/lineitem")).andDo(print()).andExpect(status().isNotFound())
		.andExpect(content().string(containsString("LineItems not Found")));

	}

}

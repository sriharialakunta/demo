package com.wipro.capstone.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.capstone.entity.Product;

@TestMethodOrder(OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
 class ProductControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	
	private List<Product> products;
	
	final ObjectMapper objectMapper = new ObjectMapper();
	

	
	@BeforeEach
	public void init() {
	
	 products = new ArrayList<Product>();
	 Product product = new Product(1,"product1","product 1 description",10);
	 Product product2 = new Product(2,"product2","product 2 description", 20);
	 products.add(product);
	 products.add(product2);
	 
      }
    @AfterEach
	public void empty() {
    
		products.clear();
	}
	
    
    @Test
    @Order(1)
    void testAddProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(products.get(0))))
                .andExpect(status().isCreated());
   
      
    }
    
    @Test
    @Order(3)
    void testSearchProduct() throws Exception{
   
    	int id=1;
    	
    	mockMvc.perform(get("/api/products/{id}",id)
    			.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").value(id))	
		.andExpect(jsonPath("$.prodname").value(products.get(0).getProdname()))
		.andExpect(jsonPath("$.proddescription").value(products.get(0).getProddescription()))
		.andExpect(jsonPath("$.prodprice").value(products.get(0).getProdprice()));
    		
    }
    
    @Test
    @Order(2)
    void testGetAllProducts() throws Exception{
    	
    	
    	mockMvc.perform(get("/api/products"))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    		.andExpect(jsonPath("$[0].id").value(1))	
    		.andExpect(jsonPath("$[0].prodname").value("product1"))
    		.andExpect(jsonPath("$[0].proddescription").value("product 1 description"))
    		.andExpect(jsonPath("$[0].prodprice").value(10));
        		
    		
    	

    }
    
    
    @Test
    @Order(4)
    void testUpdateProduct() throws Exception {
    	int id=1;
    	
    	
       
		mockMvc.perform(put("/api/products/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(products.get(0))))
        .andExpect(status().isOk())
        .andExpect(content().string("Product with id "+id+" updated successfully."));

        
       
    	
    }
    
    @Test
    @Order(5)
    void testDeleteProduct() throws Exception{
    	int id=1;
    	mockMvc.perform(delete("/api/products/{id}",id)
    			.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(content().string("Product with id "+id+" deleted Successfully."));
    	
    
    }
    
  
    
    
    
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

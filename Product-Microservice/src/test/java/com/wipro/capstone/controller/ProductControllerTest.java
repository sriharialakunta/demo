package com.wipro.capstone.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.capstone.entity.Product;
import com.wipro.capstone.service.ProductService;

@TestMethodOrder(OrderAnnotation.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
	
	private static Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    
    final ObjectMapper objectMapper = new ObjectMapper();

    
    @Test
    @Order(1)
    void testAddProduct() throws Exception {
        String productJson = "{\"id\":1,\"prodname\": \"Test Product\",\"proddescription\":\"Test Product description\", \"prodprice\": 10.0}";

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated());

        verify(productService, Mockito.times(1)).addProduct(Mockito.any(Product.class));
        
        log.info("Product created successfully");
    }
    
    @Test
    @Order(2)
    void testGetAllProducts() throws Exception{
    	List<Product> products=Arrays.asList(
    			new Product(1,"Apple","Very good to health",10),
    			new Product(2,"Mango","Summer Fruit",25));
    	
    	
    	
    	when(productService.getAllProducts()).thenReturn(products);
    	
    	mockMvc.perform(get("/api/products"))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    		.andExpect(jsonPath("$[0].id").value(1))	
    		.andExpect(jsonPath("$[0].prodname").value("Apple"))
    		.andExpect(jsonPath("$[0].proddescription").value("Very good to health"))
    		.andExpect(jsonPath("$[0].prodprice").value(10))
    		.andExpect(jsonPath("$[1].id").value(2))
    		.andExpect(jsonPath("$[1].prodname").value("Mango"))
    		.andExpect(jsonPath("$[1].proddescription").value("Summer Fruit"))
    		.andExpect(jsonPath("$[1].prodprice").value(25));
    		
    	
    	log.info("GetAllProducts() test case passed succesfully");	
    }
    
    @Test
    @Order(3)
    void testSearchProduct() throws Exception{
    	int id=1;
    	Product product=new Product(id,"Apple","Very good to health",10);
    	
    	when(productService.searchProduct(id)).thenReturn(product);
    	
    	mockMvc.perform(get("/api/products/{id}",id)
    			.accept(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").value(id))	
		.andExpect(jsonPath("$.prodname").value(product.getProdname()))
		.andExpect(jsonPath("$.proddescription").value(product.getProddescription()))
		.andExpect(jsonPath("$.prodprice").value(product.getProdprice()));
    	
    	log.info("Product found at id "+id+".");
    }
    
    @Test
    @Order(4)
    void testUpdateProduct() throws Exception {
    	int id=123;
    	
    	Product testProduct = new Product();
        testProduct.setProdname("Test Product");
        testProduct.setProddescription("Test Product description");
        testProduct.setProdprice(10);

        when(productService.updateProduct(id, testProduct)).thenReturn(testProduct);
       
		mockMvc.perform(put("/api/products/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct)))
        .andExpect(status().isOk())
        .andExpect(content().string("Product with id "+id+" updated successfully."));

        
        verify(productService,times(1)).updateProduct(eq(id),any(Product.class));
    
    	
    	log.info("updateProduct() test case passed successfully");
    	
    }
    
    @Test
    @Order(5)
    void testDeleteProduct() throws Exception{
    	int id=1;
    	mockMvc.perform(delete("/api/products/{id}",id)
    			.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isOk())
    	.andExpect(content().string("Product with id "+id+" deleted Successfully."));
    	
    	log.info("deleteProduct() test case passed successfully.");
    }
    

}

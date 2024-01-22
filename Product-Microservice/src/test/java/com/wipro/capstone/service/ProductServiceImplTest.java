package com.wipro.capstone.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.capstone.entity.Product;
import com.wipro.capstone.repository.ProductRepository;
import com.wipro.capstone.serviceimpl.ProductServiceImpl;
import com.wipro.capstone.exception.ResourceNotFoundException;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
	
	private static Logger log = LoggerFactory.getLogger(ProductServiceImplTest.class);
	
	@Mock
    private ProductRepository productRepository;
    
    @Mock
    private SequenceGeneratorService sequenceGeneratorService;
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Test
    @Order(1)
    void testAddProduct() {
        // Arrange
        Product product = new Product();
        product.setProdname("Mobile");
        product.setProddescription("It has very high features.");
        product.setProdprice(25000);
        
        int expectedId = 1;
        when(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME)).thenReturn(expectedId);
        
        when(productRepository.save(product)).thenReturn(product);
        
        // Act
        Product actualProduct = productService.addProduct(product);
        
        // Assert
        verify(sequenceGeneratorService).generateSequence(Product.SEQUENCE_NAME);
        verify(productRepository).save(product);
        
        assertEquals(expectedId, actualProduct.getId());
        
        //logging
        log.info("Product with id "+expectedId+" created successfully.");
    }
    
    @Test
    @Order(2)
    void testGetAllProducts() {
    	List<Product> products=Arrays.asList(
    			new Product(1,"Apple","Very good to health",10),
    			new Product(2,"Mango","Summer Fruit",25));
    	
    	
    	when(productRepository.findAll()).thenReturn(products);
    	
    	//Act
    	List<Product> result=productService.getAllProducts();
    	
    	//Assert
    	assertEquals(products,result);
    	
    	//logging
    	log.info("Test case passed successfully.");
    }
    
    @Test
    @Order(3)
    void testSearchProduct() {
        // Arrange
    	int productId=1;
    	Product product = new Product();
    	product.setId(productId);
        product.setProdname("Mobile");
        product.setProddescription("It has very high features.");
        product.setProdprice(25000);
        
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.searchProduct(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Mobile", result.getProdname());
        verify(productRepository, times(1)).findById(productId);
        
      //logging
        log.info("Product found with id " +productId+".");
    }
    
    @Test
    @Order(4)
    void testSearchProductNotFound() {
		// Arrange
		int id = 1;
		when(productRepository.findById(id)).thenReturn(Optional.empty());
		
		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> productService.searchProduct(id));
		
		//logging
        log.info("Product with id " +id+" not found.");
	}
    
    @Test
    @Order(5)
    void testUpdateProduct() {
        int id = 1;
        Product product = new Product();
        product.setProdname("Mobile");
        product.setProddescription("It has very high features.");
        product.setProdprice(25000);

        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setProdname("New Mobile");
        existingProduct.setProddescription("It has high features");
        existingProduct.setProdprice(23000);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product updatedProduct = productService.updateProduct(id, product);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(existingProduct);

        assertEquals(product.getProdname(), updatedProduct.getProdname());
        assertEquals(product.getProddescription(), updatedProduct.getProddescription());
        assertEquals(product.getProdprice(), updatedProduct.getProdprice());
        
      //logging
        log.info("Details of product with id "+id+" updated successfully.");
    }

    @Test
    @Order(6)
    void testDeleteProduct() {
        int id = 1;
        doNothing().when(productRepository).deleteById(id);

        productService.deleteProduct(id);

        verify(productRepository, times(1)).deleteById(id);
        
       //logging
        log.info("Product with id "+id+" deleted successfully.");
    }
}

package com.wipro.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.capstone.entity.Product;
import com.wipro.capstone.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	
	@Autowired
	ProductService productService;
	

	/**
	 * 
	 * @param product
	 * @return
	 */	
	@PostMapping
	public ResponseEntity<String> addProduct(@RequestBody Product product){
		productService.addProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body("Product Created Successfully");
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());	
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> searchProduct(@PathVariable int id){
		return ResponseEntity.ok(productService.searchProduct(id));
	}
	
	/**
	 * 
	 * @param id
	 * @param product
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestBody Product product){
		productService.updateProduct(id, product);
		return ResponseEntity.ok().body("Product with id "+id+" updated successfully.");
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		productService.searchProduct(id);
		productService.deleteProduct(id);
		return new ResponseEntity<>("Product with id "+id+" deleted Successfully.",HttpStatus.OK);
	}
}

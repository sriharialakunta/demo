package com.wipro.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.capstone.entity.Product;
import com.wipro.capstone.entity.ProductDto;
import com.wipro.capstone.service.ProductProducer;
import com.wipro.capstone.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductProducerController {
	@Autowired
	private ProductProducer producer;
	@Autowired
	private ProductService service;

	@PostMapping("/add")
	public ResponseEntity<Product> publish(@RequestBody ProductDto product){
//		return ResponseEntity.ok("Message sent to kafka topic");
	   return ResponseEntity.ok(producer.sendMessage(product));
	}
	
	
	@DeleteMapping("/del/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		service.searchProduct(id);
		producer.deleteProduct(id);
		return new ResponseEntity<>("Product with id "+id+" deleted Successfully.",HttpStatus.OK);
	}
}
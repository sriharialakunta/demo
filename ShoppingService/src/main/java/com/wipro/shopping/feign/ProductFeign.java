package com.wipro.shopping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.shopping.model.Product;
import com.wipro.shopping.model.ProductDto;

@FeignClient(name = "PRODUCT",url="${productService}")

public interface ProductFeign {

	@PostMapping("/add")
	public ResponseEntity<Product> publish(@RequestBody ProductDto product);
	
	
	@DeleteMapping("/del/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id);
}

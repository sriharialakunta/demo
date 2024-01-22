package com.wipro.shopping.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.shopping.model.Cart;

@FeignClient(name = "CART",url = "${cartService}")
public interface CartFeign{

	@GetMapping
	public ResponseEntity<List<Cart>> findAllCarts();

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Cart>> searchCart(@PathVariable("id") int id);

	@PostMapping
	public ResponseEntity<Cart> addCart(@RequestBody Cart cart);

	@PutMapping("/{id}")
	public ResponseEntity<Cart> updateCart(@PathVariable int id, @RequestBody Cart cart);

	@DeleteMapping("/{id}")
	public ResponseEntity<String> emptyCart(@PathVariable("id") int id);

	@GetMapping("/cust/{id}")
	public ResponseEntity<Cart> searchCartByCustId(@PathVariable("id") int custId);

	@PostMapping("/{custId}")
	public ResponseEntity<Cart> addCartByCustId(@PathVariable("custId")int custId, @RequestBody Cart cart);
	
	@DeleteMapping("cust/{id}")
	public ResponseEntity<String> emptyCartByCustId(@PathVariable("id") int id);
	
}

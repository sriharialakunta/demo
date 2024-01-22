package com.wipro.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.capstone.entity.Cart;
import com.wipro.capstone.service.CartKafkaService;
import com.wipro.capstone.service.LineItemService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class KafkaController {

	@Autowired
	private CartKafkaService service;

	@Autowired
	private LineItemService itemService;

	@GetMapping("/cust/{id}")
	public ResponseEntity<Cart> searchCartByCustId(@PathVariable("id") int custId) {
		log.info("Details Of Cart By Customer Id " + custId);
		return ResponseEntity.status(HttpStatus.OK).body(service.searchCartByCustId(custId));
	}

	@PostMapping("/{custId}")
	public ResponseEntity<Cart> addCartByCustId(@PathVariable("custId")int custId, @RequestBody Cart cart) {
		log.info("Creating New Cart "+custId);
		
		Cart newcart = service.addCartByKafka(custId, cart);
		if (!cart.getLineItems().isEmpty()) {
			cart.getLineItems().stream().forEach(x -> x.setCart(newcart));
		itemService.saveAllLineItems(cart.getLineItems());}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(newcart);
	}

	@DeleteMapping("cust/{id}")
	public ResponseEntity<String> emptyCartByCustId(@PathVariable("id") int id) {

		log.info("Deleting Cart with this Customer id " + id);
		searchCartByCustId(id);
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteCartByCustId(id));

	}

}

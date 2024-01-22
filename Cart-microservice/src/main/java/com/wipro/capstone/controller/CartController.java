package com.wipro.capstone.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.wipro.capstone.entity.Cart;
import com.wipro.capstone.service.CartService;
import com.wipro.capstone.service.LineItemService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

	@Autowired
	private CartService service;

	@Autowired
	private LineItemService itemService;

	@GetMapping
	public ResponseEntity<List<Cart>> findAllCarts() {
		log.info("List Of Cart");
		return ResponseEntity.status(HttpStatus.OK).body(service.findAllCarts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Cart>> searchCart(@PathVariable("id") int id) {
		log.info("Details Of Cart " + id);
		return ResponseEntity.status(HttpStatus.OK).body(service.searchCart(id));
	}

	@PostMapping("/")
	public ResponseEntity<Cart> addCart(@RequestBody Cart cart, UriComponentsBuilder ucBuilder) {
		log.info("Creating New Cart ");
		Cart newcart = service.addCart(cart);
		if (!cart.getLineItems().isEmpty())
			addAllLineItems(cart, newcart);

		return ResponseEntity.status(HttpStatus.CREATED)
//				.location(ucBuilder.path("/api/cart/{id}").buildAndExpand(newcart.getCartId()).toUri())
				.body(newcart);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cart> updateCart(@PathVariable int id, @RequestBody Cart cart,
			UriComponentsBuilder ucBuilder) {

		log.info("Updating Cart Details of id :" + id);
		cart.setCartId(searchCart(id).getBody().get().getCartId());
		Cart updatedcart = service.addCart(cart);
		if (!cart.getLineItems().isEmpty())
			addAllLineItems(cart, updatedcart);
		return ResponseEntity.status(HttpStatus.OK)
//				.location(ucBuilder.path("/api/cart/{id}").buildAndExpand(updatedcart.getCartId()).toUri())
				.body(updatedcart);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> emptyCart(@PathVariable("id") int id) {

		log.info("Deleting Cart with this id " + id);
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteCartById(id));

	}

	@DeleteMapping
	public ResponseEntity<String> emptyAllCart() {

		log.info("Deleting All Carts");
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteAllCarts());
	}

	private void addAllLineItems(Cart cart, Cart updatedcart) {
		cart.getLineItems().stream().forEach(x -> x.setCart(updatedcart));
		itemService.saveAllLineItems(cart.getLineItems());
	}

}

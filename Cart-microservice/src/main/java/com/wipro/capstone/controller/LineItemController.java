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

import com.wipro.capstone.entity.LineItem;
import com.wipro.capstone.service.CartService;
import com.wipro.capstone.service.LineItemService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cart/lineitem")
@Slf4j
public class LineItemController {

	@Autowired
	private LineItemService service;
	@Autowired
	private CartService cartService;
	
	@GetMapping
	public ResponseEntity<List<LineItem>> findAllLineItems() {
		log.info("List Of LineItems");
		return ResponseEntity.status(HttpStatus.OK).body(service.findAllLineItems());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<LineItem>> searchLineItem(@PathVariable("id") int id) {
		log.info("Details Of LineItem " + id);
		return ResponseEntity.status(HttpStatus.OK).body(service.searchLineItem(id));
	}

	@PostMapping
	public ResponseEntity<LineItem> addLineItem(@RequestBody LineItem item, UriComponentsBuilder ucBuilder) {
		log.info("Creating New LineItem ");
		cartService.searchCart(item.getCart().getCartId());
		LineItem newItem = service.addLineItem(item);
		return ResponseEntity.status(HttpStatus.CREATED)
				.location(ucBuilder.path("/api/lineitem/{id}").buildAndExpand(newItem.getItemId()).toUri())
				.body(newItem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LineItem> updateLineItem(@PathVariable int id,@RequestBody LineItem item, UriComponentsBuilder ucBuilder) {
		log.info("Updating LineItem Details of id :" + item.getItemId());
		item.setItemId(searchLineItem(id).getBody().get().getItemId());
		LineItem newLineItem = service.addLineItem(item);
		return ResponseEntity.status(HttpStatus.OK)
				.location(ucBuilder.path("/api/lineitem/{id}").buildAndExpand(newLineItem.getItemId()).toUri())
				.body(newLineItem);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteteLineItem(@PathVariable("id") int id) {
		log.info("Deleting LineItem with this id " + id);
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteLineItemById(id));

	}

	@DeleteMapping
	public ResponseEntity<String> deleteteAllLineItems() {
		log.info("Deleting All LineItems");
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteAllLineItems());
	}

}

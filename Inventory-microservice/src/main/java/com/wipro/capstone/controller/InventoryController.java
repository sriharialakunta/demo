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

import com.wipro.capstone.entity.Inventory;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

	@Autowired
	private InventoryService service;
	
	/**
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Inventory>> findAllInventories() {

		List<Inventory> inventories = service.findAllInventories();
		if (inventories.isEmpty()) {
			log.error("EMPTY Inventories List");
			throw new ResourceNotFoundException("Inventories");
		}
		log.info("List Of Inventories");
		return ResponseEntity.status(HttpStatus.OK).body(inventories);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Inventory>> searchInventory(@PathVariable("id") int id) {

		Optional<Inventory> inventorie = service.searchInventory(id);
		if (inventorie.isEmpty()) {
			log.error("Inventory with id : " + id + " not found");
			throw new ResourceNotFoundException("Inventory with id : " + id);
		}
		log.info("Details Of Inventory " + id);
		return ResponseEntity.status(HttpStatus.OK).body(inventorie);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<Inventory> searchInventoryByProductId(@PathVariable("productId") int productId) {

		Inventory inventorie = service.searchInventoryByProductId(productId);
		if (inventorie == null) {
			log.error("Inventory with productId : " + productId + " not found");
			throw new ResourceNotFoundException("Inventory with this productId : " + productId);
		}
		log.info("Details Of Inventory with this productId : " + productId);
		return ResponseEntity.status(HttpStatus.OK).body(inventorie);
	}

	/**
	 * 
	 * @param inventory
	 * @param ucBuilder
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory, UriComponentsBuilder ucBuilder) {

		log.info("Creating New Inventory " + inventory.getProductId());

		Inventory acc = service.addInventory(inventory);
		return ResponseEntity.status(HttpStatus.CREATED)
				.location(ucBuilder.path("/api/inventory/{id}").buildAndExpand(acc.getInventoryid()).toUri())
				.body(acc);
	}

	/**
	 * 
	 * @param id
	 * @param inventory
	 * @param ucBuilder
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable int id,@RequestBody Inventory inventory, UriComponentsBuilder ucBuilder) {
		log.info("Updating Inventory Details of id :" + inventory.getInventoryid());
		inventory.setInventoryid(
		searchInventory(id).getBody().get().getInventoryid());
		Inventory acc = service.addInventory(inventory);
		return ResponseEntity.status(HttpStatus.OK)
				.location(ucBuilder.path("/api/inventory/{id}").buildAndExpand(acc.getInventoryid()).toUri())
				.body(acc);

	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInventory(@PathVariable("id") int id) {

		log.info("Fetching & Deleting Inventory with id " + id);
		if (!searchInventory(id).hasBody()) {
			log.error("Unable to delete. Inventory with this id " + id + " not found");
			throw new ResourceNotFoundException("Unable to delete. Inventory with this id " + id);
		}
		log.info("Deleted Inventory with this id " + id);
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteInventoryById(id));

	}
	/**
	 * 
	 * @return
	 */
	@DeleteMapping
	public ResponseEntity<String> deleteAllInventories() {

		log.info("Deleting All Inventories");
		return ResponseEntity.status(HttpStatus.OK).body(service.deleteAllInventories());
	}

}

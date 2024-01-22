package com.wipro.capstone.service;

import java.util.List;
import java.util.Optional;

import com.wipro.capstone.entity.Inventory;

public interface InventoryService {

	List<Inventory> findAllInventories();

	Optional<Inventory> searchInventory(int id);

	Inventory addInventory(Inventory inventory);

	String deleteInventoryById(int id);

	String deleteAllInventories();

	Inventory searchInventoryByProductId(int productId);


}

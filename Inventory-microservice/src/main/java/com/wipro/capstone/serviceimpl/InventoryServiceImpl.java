package com.wipro.capstone.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Inventory;
import com.wipro.capstone.repository.InventoryRepository;
import com.wipro.capstone.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository repository;

	@Override
	public List<Inventory> findAllInventories() {
		return repository.findAll();
	}
	
	@Override
	public Optional<Inventory> searchInventory(int id) {
		return repository.findById(id);
	}

	@Override
	public Inventory searchInventoryByProductId(int productId) {
		return repository.findByProductId(productId);
	}

	@Override
	public Inventory addInventory(Inventory inventory) {
		return repository.save(inventory);
	}

	@Override
	public String deleteInventoryById(int id) {
		repository.deleteById(id);
		return "Deleted Inventory with id : " + id;

	}

	@Override
	public String deleteAllInventories() {

		repository.deleteAll();
		return "Deleted All Inventories";
	}

}

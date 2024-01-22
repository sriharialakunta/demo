package com.wipro.capstone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.capstone.entity.Inventory;
import com.wipro.capstone.repository.InventoryRepository;
import com.wipro.capstone.serviceimpl.InventoryServiceImpl;

@SpringBootTest
class InventoryServiceTest {

	@Mock
	InventoryRepository repository;

	@InjectMocks
	InventoryServiceImpl serviceImpl;

	public List<Inventory> inventories;

	@BeforeEach
	public void setAllInventories() {
		inventories = new ArrayList<Inventory>();
		Inventory inventory = new Inventory(1, 1, 3);
		Inventory inventory2 = new Inventory(2, 2, 5);
		inventories.add(inventory);
		inventories.add(inventory2);
	}

	@AfterEach
	public void emptyInventories() {
		inventories.clear();
	}

	@DisplayName("JUnit test for getAllInventories method")
	@Test
	void test_getAllInventories() {

		assertTrue(serviceImpl.findAllInventories().isEmpty());
		
		when(repository.findAll()).thenReturn(inventories);
		assertEquals(inventories, serviceImpl.findAllInventories());
		
	}

	@DisplayName("JUnit test for searchInventoryById method")
	@Test
	void test_searchInventory() {

		assertTrue(serviceImpl.searchInventory(6).isEmpty());
		
		when(repository.findById(1)).thenReturn(Optional.of(inventories.get(1)));
		assertEquals(inventories.get(1), serviceImpl.searchInventory(1).get());
		
	}

	@DisplayName("JUnit test for saveInventory method")
	@Test
	void test_saveInventory() {
		when(repository.save(inventories.get(0))).thenReturn(inventories.get(0));
		assertEquals(inventories.get(0), serviceImpl.addInventory(inventories.get(0)));
	}

	@DisplayName("JUnit test for deleteInventory method")
	@Test
	void test_deleteInventory() {
		assertEquals("Deleted Inventory with id : 1", serviceImpl.deleteInventoryById(1));
	}

	@DisplayName("JUnit test for deleteAllInventories method")
	@Test
	void test_deleteAllInventories() {
		assertEquals("Deleted All Inventories", serviceImpl.deleteAllInventories());
	}
}

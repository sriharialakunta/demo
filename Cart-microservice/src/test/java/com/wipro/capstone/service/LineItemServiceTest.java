package com.wipro.capstone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.wipro.capstone.entity.Cart;
import com.wipro.capstone.entity.LineItem;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.repository.LineItemRepository;
import com.wipro.capstone.serviceimpl.LineItemServiceImpl;

@SpringBootTest
class LineItemServiceTest {


	@Mock
	private LineItemRepository repository;
	
	@InjectMocks
	private LineItemServiceImpl serviceImpl;
	
	private List<LineItem> items = new  ArrayList<LineItem>();;
	
	@BeforeEach
	public void setAllLineItems() {

		LineItem item = new LineItem(1,1,"boll",4,10.00,new Cart());
		LineItem item2 = new LineItem(2,2,"hii",2,8.00,new Cart());
		items.add(item);
		items.add(item2);
	}

	@AfterEach
	public void emptyLineItems() {
		items.clear();
	}
	
	@DisplayName("JUnit test for getAllLineItems method")
	@Test
	void test_getAllLineItems() {

		assertThrows(ResourceNotFoundException.class, () -> {serviceImpl.findAllLineItems();});
		
		when(repository.findAll()).thenReturn(items);
		assertEquals(items, serviceImpl.findAllLineItems());
		
	}

	@DisplayName("JUnit test for searchLineItem method")
	@Test
	void test_searchLineItem() {

		assertThrows(ResourceNotFoundException.class, () -> {serviceImpl.searchLineItem(0);});
		
		when(repository.findById(1)).thenReturn(Optional.of(items.get(1)));
		assertEquals(items.get(1), serviceImpl.searchLineItem(1).get());
		
	}

	@DisplayName("JUnit test for saveLineItem method")
	@Test
	void test_saveLineItem() {
		when(repository.save(items.get(0))).thenReturn(items.get(0));
		assertEquals(items.get(0), serviceImpl.addLineItem(items.get(0)));
	}

	@DisplayName("JUnit test for deleteAllLineItem method")
	@Test
	void test_deleteLineItem() {
		assertThrows(ResourceNotFoundException.class, () -> {serviceImpl.deleteLineItemById(6);});
		
		when(repository.findById(1)).thenReturn(Optional.of(items.get(1)));
		assertEquals("Deleted lineitem with id : 1", serviceImpl.deleteLineItemById(1));
	}

	@DisplayName("JUnit test for deleteAllLineItems method")
	@Test
	void test_deleteAllLineItems() {
		assertEquals("Deleted All lineitems", serviceImpl.deleteAllLineItems());
	}
	
	
}









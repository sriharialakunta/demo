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
import com.wipro.capstone.repository.CartRepository;
import com.wipro.capstone.serviceimpl.CartServiceImpl;


@SpringBootTest
class CartServiceTest {
	
	
	@Mock
	private CartRepository repository;
	
	@InjectMocks
	private CartServiceImpl serviceImpl;
	
	private List<Cart> carts = new  ArrayList<Cart>();
	
	@BeforeEach
	public void setAllCarts() {

		Cart inventory = new Cart(1, List.of(new LineItem(1,1,"boll",4,10.00,new Cart())));
		Cart inventory2 = new Cart(2, List.of(new LineItem(2,5,"bat",6,17.00,new Cart())));
		carts.add(inventory);
		carts.add(inventory2);
	}

	@AfterEach
	public void emptyCarts() {
		carts.clear();
	}
	@DisplayName("JUnit test for getAllCarts method")
	@Test
	void test_getAllCarts() {

		assertThrows(ResourceNotFoundException.class, () -> {serviceImpl.findAllCarts();});
		
		when(repository.findAll()).thenReturn(carts);
		assertEquals(carts, serviceImpl.findAllCarts());
		
	}

	@DisplayName("JUnit test for searchCart method")
	@Test
	void test_searchCart() {

		assertThrows(ResourceNotFoundException.class, () -> {serviceImpl.searchCart(6);});
		
		when(repository.findById(1)).thenReturn(Optional.of(carts.get(1)));
		assertEquals(carts.get(1), serviceImpl.searchCart(1).get());
		
	}

	@DisplayName("JUnit test for saveCart method")
	@Test
	void test_saveCart() {
		when(repository.save(carts.get(0))).thenReturn(carts.get(0));
		assertEquals(carts.get(0), serviceImpl.addCart(carts.get(0)));
	}

	@DisplayName("JUnit test for deleteCart method")
	@Test
	void test_deleteCart() {
		assertThrows(ResourceNotFoundException.class, () -> {serviceImpl.searchCart(6);});
		
		when(repository.findById(1)).thenReturn(Optional.of(carts.get(1)));
		assertEquals("Deleted cart with id : 1", serviceImpl.deleteCartById(1));
	}

	@DisplayName("JUnit test for deleteAllCarts method")
	@Test
	void test_deleteAllCarts() {
		assertEquals("Deleted All Carts", serviceImpl.deleteAllCarts());
	}
}

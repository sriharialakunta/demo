package com.wipro.capstone.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Cart;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.repository.CartRepository;
import com.wipro.capstone.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository repository;

	@Override
	public List<Cart> findAllCarts() {

		List<Cart> carts = repository.findAll();

		if (carts.isEmpty()) {
			log.error("EMPTY Carts List");
			throw new ResourceNotFoundException("Carts");
		}

		return carts;
	}

	@Override
	public Optional<Cart> searchCart(int id) {

		Optional<Cart> cart = repository.findById(id);
		if (cart.isEmpty()) {
			log.error("Cart with id : " + id + " not found");
			throw new ResourceNotFoundException("Cart with id : " + id);
		}
		
		return cart;
	}

	@Override
	public Cart addCart(Cart cart) {
		return repository.save(cart);
	}

	@Override
	public String deleteCartById(int id) {
		
		searchCart(id);
		repository.deleteById(id);
		return "Deleted cart with id : " + id;
	}

	@Override
	public String deleteAllCarts() {
		repository.deleteAll();
		return "Deleted All Carts";
	}

}

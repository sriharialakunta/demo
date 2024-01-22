package com.wipro.capstone.service;

import java.util.List;
import java.util.Optional;

import com.wipro.capstone.entity.Cart;

public interface CartService {

	List<Cart> findAllCarts();

	Optional<Cart> searchCart(int id);

	Cart addCart(Cart cart);

	String deleteCartById(int id);

	String deleteAllCarts();

}

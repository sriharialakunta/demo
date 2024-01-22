package com.wipro.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.capstone.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	

}

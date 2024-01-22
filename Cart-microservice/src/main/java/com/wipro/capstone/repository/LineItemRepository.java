package com.wipro.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.capstone.entity.LineItem;

public interface LineItemRepository extends JpaRepository<LineItem, Integer> {
	

}

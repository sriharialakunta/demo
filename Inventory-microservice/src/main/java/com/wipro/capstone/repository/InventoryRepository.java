package com.wipro.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.capstone.entity.Inventory;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	Inventory findByProductId(int productid);
	

}

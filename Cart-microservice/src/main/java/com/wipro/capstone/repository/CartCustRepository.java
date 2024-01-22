package com.wipro.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.capstone.entity.CartCust;

public interface CartCustRepository extends JpaRepository<CartCust, Integer> {

	CartCust findByCustId(int custId);

}

package com.wipro.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wipro.order.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

//	Optional<Order> findById(String orderId);
//
//	void deleteById(String orderId);

}

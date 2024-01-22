package com.wipro.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wipro.order.model.LineItem;

@Repository
public interface LineItemRepository extends MongoRepository<LineItem, String> {

}

package com.wipro.capstone.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.wipro.capstone.entity.Product;

/**
 * 
 * @author GO20390950
 *
 */
public interface ProductRepository extends MongoRepository<Product, Integer>{

}

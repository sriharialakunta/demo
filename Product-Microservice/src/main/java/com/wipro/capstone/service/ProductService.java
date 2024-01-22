package com.wipro.capstone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Product;

@Service
public interface ProductService {
	
	public Product addProduct(Product product);

	public List<Product> getAllProducts();
	
	public Product searchProduct(int id);
	
	public Product updateProduct(int id,Product product);
	
	public void deleteProduct(int id);
}

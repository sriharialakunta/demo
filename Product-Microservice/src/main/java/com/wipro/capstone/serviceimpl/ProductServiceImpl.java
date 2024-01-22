package com.wipro.capstone.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Product;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.repository.ProductRepository;
import com.wipro.capstone.service.ProductService;
import com.wipro.capstone.service.SequenceGeneratorService;


@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    
	public Product addProduct(Product product) {
		product.setId(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
		return productRepository.save(product);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	
	public Product searchProduct(int id) {
		return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product with id "+id+" not found"));
	}

	
	public Product updateProduct(int id, Product product) {
		Product p=productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product with id "+id+" not found"));
		p.setProdname(product.getProdname());
		p.setProddescription(product.getProddescription());
		p.setProdprice(product.getProdprice());
		return productRepository.save(p);
	}


	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}
	
	

}

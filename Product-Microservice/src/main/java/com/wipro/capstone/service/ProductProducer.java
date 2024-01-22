package com.wipro.capstone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Product;
import com.wipro.capstone.entity.ProductDto;
import com.wipro.capstone.repository.ProductRepository;
import com.wipro.capstone.service.util.AppConstants;

@Service
public class ProductProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ProductRepository repository;

	@Autowired
    private SequenceGeneratorService sequenceGeneratorService;
	
	public Product sendMessage(ProductDto p) {
		
		LOGGER.info(String.format("Message sent -> %s", p));
		
		Product product = new Product();
		product.setId(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
		product.setProddescription(p.getProddescription());
		product.setProdname(p.getProdname());
		product.setProdprice(p.getProdprice());
		
		Product product2 = repository.save(product);
		String inventoryInput = product2.getId()+","+p.getQuantity();
		kafkaTemplate.send(AppConstants.TOPIC_ADDPRODUCT, inventoryInput);
		return product2;
	}

	public void deleteProduct(int id) {
		repository.deleteById(id);
		kafkaTemplate.send(AppConstants.TOPIC_DELETEPRODUCT, String.valueOf(id));
	}

}

package com.wipro.capstone.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Inventory;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.repository.InventoryRepository;
import com.wipro.capstone.service.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryConsumer {

	@Autowired
	private InventoryRepository repository;
	
	@KafkaListener(topics = AppConstants.TOPIC_ADDPRODUCT, groupId = AppConstants.GROUP_PRODUCT)
	public void consume(String input) {
		log.info(String.format("Product id, quantity recived -> %s", input));
		String[] inputs = input.split(",");
		System.out.println(inputs[0]);
		int productid = Integer.parseInt(inputs[0]);
		int quantity = Integer.parseInt(inputs[1]);
		repository.save(new Inventory(productid,quantity));

	}
	@KafkaListener(topics = AppConstants.TOPIC_DELETEPRODUCT, groupId = AppConstants.GROUP_PRODUCT)
	public void deleteInventory(String input) {
		log.info(String.format("Product id, quantity recived -> %s", input));
		int productid = Integer.parseInt(input);
		Inventory inventory = repository.findByProductId(productid);
		repository.deleteById(inventory.getInventoryid());

	}
	@KafkaListener(topics = AppConstants.TOPIC_ORDER_INVENTORY, groupId = AppConstants.GROUP_ORDER)
	public void updateInventory(String input) {
		log.info(String.format("Product id, quantity recived -> %s", input));
		String[] inputs = input.split(",");
		System.out.println(inputs[0]);
		for (String pro : inputs) {
			String[] in = pro.split("&");
			int productid = Integer.parseInt(in[0]); 
			int quantity = Integer.parseInt(in[1]);
			System.out.println(in[0] +"DKD"+in[1]);
			Inventory inventory = repository.findByProductId(productid);
			if(inventory.getQuantity()-quantity>=0){
			inventory.setQuantity(inventory.getQuantity()-quantity);
			repository.save(inventory);}
			else throw new ResourceNotFoundException("Insufficient Quantity : "+inventory.getQuantity()+" for ProductId : "+inventory.getProductId());
			// kafkaTemplate.send(AppConstants.TOPIC_ORDER_INVENTORY_SEND, "Insufficient Quantity");
		}

	}
}

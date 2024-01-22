package com.wipro.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Cart;
import com.wipro.capstone.exception.ResourceAlreadyExistsException;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.entity.CartCust;
import com.wipro.capstone.repository.CartCustRepository;
import com.wipro.capstone.repository.CartRepository;
import com.wipro.capstone.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartKafkaService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private CartCustRepository custRepository;

	public Cart addCartByKafka(int custId, Cart cart) {

		if(custRepository.findByCustId(custId)==null ){
			cart = repository.save(cart);
			custRepository.save(new CartCust(cart.getCartId(), custId));
		return cart;
		}
		throw new ResourceAlreadyExistsException("Cart with this custId : " + custId);
	}

	public Cart searchCartByCustId(int custId) {
	CartCust cartCust = custRepository.findByCustId(custId);
		if(cartCust !=null)
			return repository.findById(custRepository.findByCustId(custId).getCartId()).get();
		throw new ResourceNotFoundException("Cart with this custId : " + custId);
	}

	public String deleteCartByCustId(int custId) {
		CartCust id = custRepository.findByCustId(custId);
		custRepository.deleteById(id.getId());
		repository.deleteById(id.getCartId());
		return "Deleting Cart  with this Customer id " + custId;
	}

	@KafkaListener(topics = AppConstants.TOPIC_ORDER, groupId = AppConstants.GROUP_ORDER)
	public void consume(String input) {
		String[] inputs = input.split(",");
		// System.out.println(inputs[0]);
		log.info(String.format("Customer id -> %s", inputs[0]));
		int custId = Integer.parseInt(inputs[0]);
		deleteCartByCustId(custId);

	}
}

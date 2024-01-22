package com.wipro.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.order.model.Order;
import com.wipro.order.payload.OrderDTO;
import com.wipro.order.service.OrderProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class KafkaController {

	@Autowired
	private OrderProducer orderProducer;
	
	@PostMapping("/{custId}")
	public ResponseEntity<Order> addOrderByCustId(@PathVariable int custId, @RequestBody OrderDTO orderDto) {
		log.info("Order adding against customer id: + " + custId +"!");
		return new ResponseEntity<Order>(orderProducer.addOrderByCustId(custId, orderDto.DTOtoOrder()), HttpStatus.CREATED);
	}

	@GetMapping("cust/{custId}")
	public ResponseEntity<List<Order>> searchOrderByCustId(@PathVariable int custId) {
		return ResponseEntity.ok(orderProducer.searchOrderByCustId(custId));
	}
}

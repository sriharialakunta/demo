package com.wipro.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.order.model.Order;
import com.wipro.order.payload.OrderDTO;
import com.wipro.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ST20364645
 *
 */
@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * Method: Printing a list of all orders
	 * 
	 * @return list of all orders
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders() {
		log.info("List of orders is dispalyed");
		return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
	}

	/**
	 * Searching an order
	 * 
	 * @param orderId
	 * @return searched order
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Order> searchOrder(@PathVariable("id") String orderId) {
		log.info("Order details of order id: " + orderId + "are dispalyed!");
		return new ResponseEntity<Order>(orderService.getOrderById(orderId), HttpStatus.OK);
	}

	/**
	 * Adding new order
	 * 
	 * @param orderDto
	 * @return added order
	 */
	@PostMapping
	public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDto) {
		log.info("Order added!");
		return new ResponseEntity<Order>(orderService.addOrder(orderDto.DTOtoOrder()), HttpStatus.CREATED);
	}

	/**
	 * Updating an existing order
	 * 
	 * @param orderId
	 * @param orderDto
	 * @return updated order
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable("id") String orderId, @RequestBody OrderDTO orderDto) {
		log.info("Order against order id: " + orderId + " updated!");
		Order response = orderService.updateOrder(orderId, orderDto.DTOtoOrder());
		return new ResponseEntity<Order>(response, HttpStatus.OK);
	}

	/**
	 * Deleting an order
	 * 
	 * @param orderId
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable("id") String orderId) {
		log.info("Order deleted!");
		return new ResponseEntity<>(orderService.deleteOrder(orderId), HttpStatus.OK);
	}

}

package com.wipro.shopping.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.shopping.model.Order;

@FeignClient(name = "ORDER", url = "${orderService}")
public interface OrderFeign {

	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders();

	@GetMapping("/{id}")
	public ResponseEntity<Order> searchOrder(@PathVariable("id") String orderId);

	@PostMapping
	public ResponseEntity<Order> addOrder(@RequestBody Order order);

	@PutMapping("/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable("id") String orderId, @RequestBody Order order);

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable("id") String orderId);

	@PostMapping("/{custId}")
	public ResponseEntity<Order> addOrderByCustId(@PathVariable int custId, @RequestBody Order order);

	@GetMapping("cust/{custId}")
	public ResponseEntity<List<Order>> searchOrderByCustId(@PathVariable int custId);

}

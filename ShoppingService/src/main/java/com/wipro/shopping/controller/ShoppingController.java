package com.wipro.shopping.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.shopping.exception.EntityNotFoundException;
import com.wipro.shopping.feign.CartFeign;
import com.wipro.shopping.feign.CustomerFeign;
import com.wipro.shopping.feign.InventoryFeign;
import com.wipro.shopping.feign.OrderFeign;
import com.wipro.shopping.feign.ProductFeign;
import com.wipro.shopping.model.Cart;
import com.wipro.shopping.model.Inventory;
import com.wipro.shopping.model.LineItem;
import com.wipro.shopping.model.Order;
import com.wipro.shopping.model.Product;
import com.wipro.shopping.model.ProductDto;

import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/shopping-service/")
@Slf4j
public class ShoppingController {

	@Autowired
	private CartFeign cartService;

	@Autowired
	private CustomerFeign customerService;

	@Autowired
	private OrderFeign orderService;

	@Autowired
	private InventoryFeign inventoryService;

	@Autowired
	private ProductFeign productService;

	// CART Micro Service
	@GetMapping("cart/{custId}")
	@CircuitBreaker(fallbackMethod="searchCartByCustIdDefaultMessage", name = "searchCartByCustIdService")
	public ResponseEntity<Cart> searchCartByCustId(@PathVariable("custId") int custId) {
		log.info("null");
		customerService.getCustomerById(custId);
		return cartService.searchCartByCustId(custId);
	}

	@PutMapping("cart/{custId}")
	@CircuitBreaker(fallbackMethod="defaultMessage", name = "service")
	public ResponseEntity<Cart> addCartByCustId(@PathVariable("custId") int custId, @RequestBody Cart cart) {
		customerService.getCustomerById(custId);
		checkInventory(cart);
		return cartService.addCartByCustId(custId, cart);
	}

	@DeleteMapping("cart/{custId}")
	@CircuitBreaker(fallbackMethod="defaultMessage", name = "service")
	public ResponseEntity<String> emptyCartByCustId(@PathVariable("custId") int custId) {
		return cartService.emptyCartByCustId(custId);
	}

	// CUSTOMER
	@DeleteMapping("/customers/{custId}")
	@CircuitBreaker(fallbackMethod="defaultMessage", name = "service")
	public ResponseEntity<String> deleteCustomer(@PathVariable int custId) {
		cartService.emptyCartByCustId(custId);
		return customerService.deleteCustomer(custId);
	}

	// ORDER
	@PostMapping("order/{custId}")
	@CircuitBreaker(fallbackMethod="defaultMessage", name = "service")
	public ResponseEntity<Order> addOrderByCustId(@PathVariable int custId) {
		Cart cart = searchCartByCustId(custId).getBody();
		Order order = new Order();
		order.setLineItems(checkInventory(cart));
		return orderService.addOrderByCustId(custId, order);
	}

	@GetMapping("order/{custId}")
	@CircuitBreaker(fallbackMethod="defaultMessage", name = "service")
	public ResponseEntity<List<Order>> searchOrderByCustId(@PathVariable int custId) {
		return orderService.searchOrderByCustId(custId);
	}

	// PRODUCT
	@PostMapping("products")
	@CircuitBreaker(fallbackMethod="defaultMessage", name = "service")
	public ResponseEntity<Product> publish(@RequestBody ProductDto product) {
		return productService.publish(product);
	}

	private List<LineItem> checkInventory(Cart cart) {
		List<LineItem> items = new ArrayList<>();
		ArrayList<Integer> productIds=new ArrayList<>();
		productIds.add(0);
		if (!cart.getLineItems().isEmpty()) {
			
			for (LineItem item : cart.getLineItems()) {

				if(productIds.contains(item.getProductId()))
					throw new EntityNotFoundException("Please add Unique Items Only...");

				productIds.add(item.getProductId());
			
				Inventory inventory = inventoryService.searchInventoryByProductId(item.getProductId()).getBody();
				System.out.println(inventory);
				if (inventory != null && inventory.getQuantity() - item.getQuantity() < 0) {
					throw new EntityNotFoundException("Insufficient Products ProductId : " + inventory.getProductId()
							+ " Available Quantity : " + inventory.getQuantity());
				}
				items.add(
						new LineItem(item.getProductId(), item.getProductName(), item.getQuantity(), item.getPrice()));
			}
			return items;
		}
		throw new EntityNotFoundException("Add Some Items into Cart");
	}



	public ResponseEntity<Cart> searchCartByCustIdDefaultMessage(Exception ex){
		if (ex instanceof FeignException) {
			String[] message = ex.getMessage().split("]");
			log.info("this is default"+message.length);
			if(message.length == 5)
			throw new EntityNotFoundException(
				message[4].substring(3,message[4].length()));
			
		if (ex instanceof RetryableException) 
		throw new EntityNotFoundException(
			"Unable to connect Cart Microservice");
		}
		throw new EntityNotFoundException("Please Check Cart Microservice Is to Run");
	}

	public ResponseEntity<Object> defaultMessage(Exception ex){
		if (ex instanceof FeignException) {
			String[] message = ex.getMessage().split("]");
			log.info("this is default"+message.length);
			if(message.length == 5)
			throw new EntityNotFoundException(
				message[4].substring(3,message[4].length()));
			
		if (ex instanceof RetryableException) 
		throw new EntityNotFoundException(
			"Unable to connect With Microservice at this"+
			 " time Please try after some time");
		}
			throw new EntityNotFoundException(ex.getMessage());
	}
}

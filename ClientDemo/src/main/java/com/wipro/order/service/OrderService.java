package com.wipro.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.order.exceptions.EntityNotFoundException;
import com.wipro.order.model.Order;
import com.wipro.order.repository.LineItemRepository;
import com.wipro.order.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ST20364645
 *
 */
@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private LineItemRepository lineItemRepository;

	/**
	 * Method: Add new order
	 * 
	 * @param orderData
	 * @return newly created order, HTTP status 201
	 */
	public Order addOrder(Order orderData) {

		Order order = new Order();

		orderData.getLineItems().stream().forEach(Items -> lineItemRepository.save(Items));
		order.setLineItems(orderData.getLineItems());

		Order newOrder =orderRepository.save(order);
		return newOrder;

	}

	/**
	 * Method: Update an existing order
	  
	 * @param orderId
	 * @param order
	 * @return freshly updated order
	 */
	public Order  updateOrder(String orderId, Order order) {
		Order savedOrder = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order doesn't exist"));
		order.getLineItems().stream().forEach(Items -> lineItemRepository.save(Items));
		savedOrder.setLineItems(order.getLineItems());

		Order updatedOrder= orderRepository.save(savedOrder);
		return updatedOrder;
	}

	/**
	 * Method: List of all orders
	 * 
	 * @return orderList and HTTP 200 ok
	 */
	public List<Order> getAllOrders() {
		List<Order> ordersList = orderRepository.findAll();
		if (ordersList.isEmpty()) {
			log.error("Order list is Empty");
			throw new EntityNotFoundException("Order List is Empty!!");
		}
		return ordersList;

	}

	/**
	 * Method: searching an order using its id
	 * 
	 * @param orderId
	 * @return the searched order
	 */
	public Order getOrderById(String orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order doesn't exist"));
		return order;
	}

	/**
	 * Method: Deleting an order
	 * 
	 * @param orderId
	 * @return confirmation string for order deletion and HTTP 200 ok
	 */
	public String deleteOrder(String orderId) {
		getOrderById(orderId);
		// orderRepository.findById(orderId)
		// 		.orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
//		order.getLineItems().stream().map(LineItem::getItemId)
//				.forEach(itemId -> lineItemService.deleteLineItem(itemId));
//		if(order.isPresent())
		orderRepository.deleteById(orderId);
		return "Order with id= " + orderId + " is Deleted";
	}

//	public ResponseEntity<Object> addOrder(Order orderData) {
//		Order order = new Order();
//		orderData.getLineItems().stream().forEach(lineItem -> lineItemService.addLineItem(lineItem));
//		order.setLineItems(orderData.getLineItems());
//		orderRepository.save(order);
//		return new ResponseEntity<>("Order is Created", HttpStatus.CREATED);
//	}
//
//	public ResponseEntity<Order> searchOrder(long id) {
//		Order Order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
//		return new ResponseEntity<>(Order, HttpStatus.OK);
//	}
//
//	public ResponseEntity<Object> deleteOrder(long id) {
//		Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
//		order.getLineItems().stream().map(LineItem::getItemId)
//				.forEach(itemId -> lineItemService.deleteLineItem(itemId));
//		orderRepository.deleteById(id);
//		return new ResponseEntity<>("Order with id= " + id + " is Deleted", HttpStatus.OK);
//	}
//
//	public ResponseEntity<Object> updateOrder(long id, Order orderData) {
//		Order order = orderRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Order Not Found"));
//		order.getLineItems().stream().map(LineItem::getItemId).forEach(itemId->lineItemService.deleteLineItem(itemId));
//		List<LineItem> lineItems = new ArrayList<>();
//		
//		orderData.getLineItems().stream().forEach(lineItem->lineItemService.addLineItem(lineItem));
//		orderData.getLineItems().stream().forEach(lineItems::add);
//		
//		order.setLineItems (lineItems);
//		orderRepository.save(order);
//		return new ResponseEntity<>("Order with id: "+id+ " is Updated", HttpStatus.OK);
//	}
//	
//	public ResponseEntity<Object> listOrder(){
//		List<Order> list = orderRepository.findAll();
//		if (list.isEmpty()) throw new EntityNotFoundException ("No Cart Found");
//		else return new ResponseEntity<>(list, HttpStatus.OK);
//	}
}

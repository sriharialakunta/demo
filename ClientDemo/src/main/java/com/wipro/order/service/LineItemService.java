//package com.wipro.order.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.wipro.order.exceptions.EntityNotFoundException;
//import com.wipro.order.model.LineItem;
//import com.wipro.order.model.Order;
//import com.wipro.order.repository.LineItemRepository;
//import com.wipro.order.repository.OrderRepository;
//
//public class LineItemService {
//
//	@Autowired
//	private OrderRepository orderRepository;
//
//	@Autowired
//	private LineItemRepository lineItemRepository;
//
//	public LineItem addLineItem(long orderId, LineItem lineItemData) {
//
//		Optional<Order> order = Optional.ofNullable(orderRepository.findById(orderId)
//				.orElseThrow(() -> new EntityNotFoundException(" Order doesn't exist")));
//		Order newOrder = order.get();
//		newOrder.getLineItems().add(lineItemData);
//		return orderRepository.saveAll(lineItemData);
//	}
//
//	public void deleteLineItem(Long itemId) {
//
//		Optional<LineItem> lineItem = Optional.ofNullable(lineItemRepository.findById(itemId)
//				.orElseThrow(() -> new EntityNotFoundException(" Order doesn't exist")));
//		if(lineItem.isPresent())
//			lineItemRepository.deleteById(itemId);
//	}
//
//}

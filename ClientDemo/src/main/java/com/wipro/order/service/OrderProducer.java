package com.wipro.order.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.wipro.order.exceptions.EntityNotFoundException;
import com.wipro.order.model.CustomerOrder;
import com.wipro.order.model.LineItem;
import com.wipro.order.model.Order;
import com.wipro.order.repository.CustomerOrderRepository;
import com.wipro.order.repository.LineItemRepository;
import com.wipro.order.repository.OrderRepository;
import com.wipro.order.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProducer {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private LineItemRepository lineItemRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;	

	public Order addOrderByCustId(int custId, Order order) {

		//Order order = new Order();

		order.getLineItems().stream().forEach(Items -> lineItemRepository.save(Items));
		//order.setLineItems(order.getLineItems());

		
		Order order1 = orderRepository.save(order);
		CustomerOrder customerOrder = new CustomerOrder(custId, order1.getOrderId());
		customerOrderRepository.save(customerOrder);
		kafkaTemplate.send(AppConstants.TOPIC_ORDER, ""+custId);
		String customerString ="";
		for (LineItem item : order.getLineItems()) {
			customerString = customerString+item.getProductId() + "&"
			+ item.getQuantity()+",";
		}
		log.info(String.format("Message sent to Inventory -> %s", customerString));
		kafkaTemplate.send(AppConstants.TOPIC_ORDER_INVENTORY, customerString);
		return order1;
	}

	public List<Order> searchOrderByCustId(int custId) {
		List<CustomerOrder> custs = customerOrderRepository.findByCustId(custId);
			if(!custs.isEmpty()){
			List<Order> orders = new ArrayList<>();
				for(CustomerOrder customer : custs){
					orders.add(orderRepository.findById(customer.getOrderId()).get());
				}
			return orders;}

			throw new EntityNotFoundException("Cart with this custId : " + custId);
	}

}

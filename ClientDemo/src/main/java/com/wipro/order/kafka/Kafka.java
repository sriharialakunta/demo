//package com.wipro.order.kafka;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import com.wipro.order.model.Order;
//import com.wipro.order.service.OrderService;
//
//@Service
//public class Kafka {
//
//	private Logger logger = LoggerFactory.getLogger(Kafka.class);
//	
//	@Autowired
//	private OrderService orderService;
//	
//	@KafkaListener(topics = "${kafka.topic.inventory}" , autoStartup = "false")
//	public void orderConsumer(Order order) {
//		orderService.updateOrder(order.getOrderId(), order);
//		
//		logger.info(String.format("Order recieved", order));
//	}
//}

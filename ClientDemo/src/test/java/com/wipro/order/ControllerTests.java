package com.wipro.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.order.controller.OrderController;
import com.wipro.order.model.LineItem;
import com.wipro.order.model.Order;
import com.wipro.order.payload.OrderDTO;
import com.wipro.order.repository.OrderRepository;
import com.wipro.order.service.OrderService;

@SpringBootTest(classes = com.wipro.order.OrderApplication.class)
@DisplayName("OrderControllerTest")
@ActiveProfiles("test")
 class ControllerTests {
	
	@Mock
	OrderService orderService;
	
	@Mock
	OrderRepository orderRepository;

	@InjectMocks
	private OrderController orderController;

	Order order=getOrder();
	Order savedOrder = new Order();

	@BeforeEach
	public void startOrder() {
		savedOrder = orderRepository.save(order);
	}
	
	@AfterEach
	public void stopOrder() {
		orderRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		assertThat(orderController).isNotNull();
	}

	@Test
	@DisplayName("Test-> Save Order")
	void saveOrderTest() {
		OrderDTO OrderDTO = new OrderDTO();
		orderController.addOrder(OrderDTO);
		
		verify(orderService, times(1)).addOrder(OrderDTO.DTOtoOrder());
	}
	
	@Test
	@DisplayName("Test-> Search Order By Id")
	void searchOrderByIdTest() {
		orderController.searchOrder("123");
		verify(orderService, times(1)).getOrderById("123");
	}
	
	@Test
	@DisplayName("Test-> Update Order By Id")
	void updateOrderTest() {
		
		OrderDTO OrderDTO = new OrderDTO();
		OrderDTO.setOrderId(order.getOrderId());
		OrderDTO.setLineItems(getLineItems());
		
		orderController.updateOrder(order.getOrderId(), OrderDTO);
		verify(orderService, times(1)).updateOrder(order.getOrderId(), OrderDTO.DTOtoOrder());
	}
	@Test
	@DisplayName("Test-> Get All Orders")
	void findAllOrdersTest() {
		orderController.getAllOrders();
		verify(orderService, times(1)).getAllOrders();
	}
	
	@Test
	@DisplayName("Test-> Delete Order By Id")
	void deleteOrderTest() {
		orderController.deleteOrder(order.getOrderId());
		verify(orderService, times(1)).deleteOrder(order.getOrderId());
	}

	
	private Order getOrder() {
		Order Order=new Order();
		return Order;
		
	}
	
	private List<LineItem> getLineItems() {
		List<LineItem> list = new ArrayList<>();
		LineItem lineItem = new LineItem();
		lineItem.setProductId(1001L);
		lineItem.setProductName("Samsung Galaxy");
		lineItem.setQuantity(2L);
		lineItem.setPrice(100);
		list.add(lineItem);
		return list;
		
	}

}

package com.wipro.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.order.exceptions.EntityNotFoundException;
import com.wipro.order.model.LineItem;
import com.wipro.order.model.Order;
import com.wipro.order.repository.LineItemRepository;
import com.wipro.order.repository.OrderRepository;
import com.wipro.order.service.OrderService;

@SpringBootTest(classes = com.wipro.order.OrderApplication.class)
@DisplayName("OrderServiceTest")
@ActiveProfiles("test")
public class ServiceTests {

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private LineItemRepository lineItemRepository;
	@InjectMocks
	private OrderService orderService;

	@Test
	void should_find_and_return_all_orders() {
//		orderService.getAllOrders();
//		verify(orderRepository).findAll();

		when(orderRepository.findAll()).thenReturn(List.of(new Order(), new Order()));

		assertThat(orderService.getAllOrders()).hasSize(2);
		verify(orderRepository, times(1)).findAll();
		verifyNoMoreInteractions(orderRepository);
	}

	@Test
	public void testGetAllOrders() {
		// Create mock data
		List<Order> orders = Arrays.asList(new Order(), new Order(), new Order());

		// Set up mock behavior for the repository
		Mockito.when(orderRepository.findAll()).thenReturn(orders);

		// Call the service method

		List<Order> result = orderService.getAllOrders();

		// Check the result
		assertEquals(3, result.size());
		assertEquals(orders.get(0), result.get(0));
		assertEquals(orders.get(1), result.get(1));
		assertEquals(orders.get(2), result.get(2));

		// Verify that the repository method was called
		Mockito.verify(orderRepository).findAll();
	}

	@Test
	void should_save_one_order() {
		List<LineItem> listLineItems = new ArrayList<>();
		LineItem lineItem = new LineItem("1", 1L, "pencil", 2L, 50);
		listLineItems.add(lineItem);
		Order newOrder = new Order("1", listLineItems);
		when(orderRepository.save(any(Order.class))).thenReturn(newOrder);

		Order addOrder = orderService.addOrder(newOrder);

		assertThat(addOrder).usingRecursiveComparison().isEqualTo(newOrder);
		verify(orderRepository, times(1)).save(any(Order.class));
		verifyNoMoreInteractions(orderRepository);
	}

	@Test
	void should_find_and_return_one_order() {
		List<LineItem> listLineItems = new ArrayList<>();
		LineItem lineItem = new LineItem("1", 1L, "pencil", 2L, 50);
		listLineItems.add(lineItem);
		Order newOrder = new Order("1", listLineItems);
		when(orderRepository.findById(anyString())).thenReturn(Optional.of(newOrder));

		Order savedOrder = orderService.getOrderById(getRandomString());

		assertThat(savedOrder).usingRecursiveComparison().isEqualTo(newOrder);
		verify(orderRepository, times(1)).findById(anyString());
		verifyNoMoreInteractions(orderRepository);
	}

	@Test
	void should_not_found_a_student_that_doesnt_exists() {
		// Arrange
		when(orderRepository.findById(anyString())).thenReturn(Optional.empty());

		// Act & Assert
		Assertions.assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(getRandomString()));
		verify(orderRepository, times(1)).findById(anyString());
		verifyNoMoreInteractions(orderRepository);
	}

	@Test
	void should_delete_one_order() {
		// Arrange
	    // Arrange
	    String orderId = getRandomString();
	    Order order = new Order();
	    order.setOrderId(orderId);

	    Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
	    doNothing().when(orderRepository).deleteById(orderId);

	    // Act & Assert
	    orderService.deleteOrder(orderId);

	    verify(orderRepository, times(1)).findById(orderId);
	    verify(orderRepository, times(1)).deleteById(orderId);
	    verifyNoMoreInteractions(orderRepository);
	}

	private String getRandomString() {
		return new Random().ints(1, 10).findFirst().toString();
	}

}

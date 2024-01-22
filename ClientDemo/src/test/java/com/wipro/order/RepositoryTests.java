package com.wipro.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.order.model.Order;
import com.wipro.order.repository.OrderRepository;

@SpringBootTest(classes = com.wipro.order.OrderApplication.class)
@DisplayName("OrderRepositoryTest")
@ActiveProfiles("test")
public class RepositoryTests {

	@Autowired
	OrderRepository orderRepository;

	Order order = getOrder();

	@BeforeEach
	public void startOrder() {
		order = orderRepository.save(order);
	}

	@AfterEach
	public void stopOrder() {
		orderRepository.deleteAll();
	}

	@Test
	@DisplayName("Test-> Save Order")
	void testSave() {
		Order result = orderRepository.findById(order.getOrderId()).get();
		assertEquals(order.getOrderId(), result.getOrderId());
	}

	@Test
	@DisplayName("Test-> Find Order By Id")
	void testFindById() {
		Order result = orderRepository.findById(order.getOrderId()).get();
		assertEquals(order.getOrderId(), result.getOrderId());
	}

	@Test
	@DisplayName("Test-> Get All Orders")
	void testFindAll() {
		List<Order> result = new ArrayList<Order>();
		orderRepository.findAll().forEach(e -> result.add(e));
		assertThat(result).hasSize(1);
	}

	@Test
	@DisplayName("Test-> Delete Order By Id")
	void testDeleteById() {
		Order result = orderRepository.findById(order.getOrderId()).get();
		orderRepository.deleteById(result.getOrderId());
		List<Order> result1 = new ArrayList<>();
		orderRepository.findAll().forEach(e -> result1.add(e));
		assertEquals(0, result1.size());
	}

	private Order getOrder() {
		Order order = new Order();
		return order;

	}

}

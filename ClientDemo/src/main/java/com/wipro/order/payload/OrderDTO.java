package com.wipro.order.payload;

import java.util.ArrayList;
import java.util.List;

import com.wipro.order.model.LineItem;
import com.wipro.order.model.Order;

import lombok.Data;

@Data
public class OrderDTO {
	
	private String orderId;
	
	private List<LineItem> lineItems = new ArrayList<>();
	
	public Order DTOtoOrder() {
		Order order= new Order();
		order.setOrderId(this.orderId);
		order.setLineItems(this.lineItems);
		return order;
	}
}

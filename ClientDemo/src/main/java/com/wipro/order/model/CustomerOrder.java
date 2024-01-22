package com.wipro.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ST20364645
 *
 */
@Document("Customer-Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {

	@Id
	private String id;
	private int custId;
	private String orderId;

	public CustomerOrder(int custId, String orderId) {
		super();
		this.custId = custId;
		this.orderId = orderId;
	}

}

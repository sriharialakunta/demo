package com.wipro.order.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ST20364645
 *
 */
@Document("Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	private String orderId;
	
	@DocumentReference(collection = "LineItem")
	private List<LineItem> lineItems = new ArrayList<>();
}

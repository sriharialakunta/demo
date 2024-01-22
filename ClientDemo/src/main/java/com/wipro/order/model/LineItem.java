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

@Document("LineItem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItem {

	@Id
	private String itemId;

	private long productId;
	private String productName;
	private long quantity;
	private double price;

}

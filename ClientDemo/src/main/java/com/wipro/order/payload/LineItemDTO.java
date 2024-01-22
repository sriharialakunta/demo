package com.wipro.order.payload;

import lombok.Data;

@Data
public class LineItemDTO {

	private String itemId;

	private long productId;
	private String productName;
	private long quantity;
	private double price;

}

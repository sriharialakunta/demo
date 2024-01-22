package com.wipro.shopping.model;

import lombok.Data;

@Data
public class LineItem {
	private String itemId;

	private int productId;
	private String productName;
	private long quantity;
	private double price;
	public LineItem(int productId, String productName, long quantity, double price) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
}

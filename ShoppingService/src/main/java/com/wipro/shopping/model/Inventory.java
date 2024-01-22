package com.wipro.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

	private int inventoryid;
	private int productId;
	private int quantity;

	// public Inventory(int productId, int quantity) {
	// 	this.productId = productId;
	// 	this.quantity = quantity;
	// }
	  
}

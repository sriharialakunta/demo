package com.wipro.capstone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int inventoryid;
	private int productId;
	private int quantity;

	public Inventory(int productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	  
}

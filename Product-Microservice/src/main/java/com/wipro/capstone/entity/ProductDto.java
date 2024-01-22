package com.wipro.capstone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	
	private int id;
	
	private String prodname;
	
	private String proddescription;
	
	private float prodprice;
	
	private int quantity;
	

}

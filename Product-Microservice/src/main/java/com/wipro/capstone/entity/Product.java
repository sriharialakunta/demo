package com.wipro.capstone.entity;

import org.springframework.data.mongodb.core.mapping.Document;



import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
public class Product {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	private int id;
	
	private String prodname;
	
	private String proddescription;
	
	private float prodprice;
	

}

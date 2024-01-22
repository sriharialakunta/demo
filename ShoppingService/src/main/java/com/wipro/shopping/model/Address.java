package com.wipro.shopping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private int addressid;
	
	private String dno;


    private String streetname;


    private String layout;
    
    private String city;
    
    private String pincode;
   
}

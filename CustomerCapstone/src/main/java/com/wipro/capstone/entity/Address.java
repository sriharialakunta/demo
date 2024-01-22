package com.wipro.capstone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressid;
	
	@Column(name ="dno")
	private String dno;

    @Column(name = "streetname")
    private String streetname;

    @Column(name = "layout")
    private String layout;
    
    @Column(name="City")
    private String city;
    
    @Column(name="pincode")
    private String pincode;
   
}

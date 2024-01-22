package com.wipro.capstone.entity;

import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;
    

    @ManyToMany(targetEntity=Address.class, cascade=CascadeType.ALL)
    @JoinColumn(name="addressid",referencedColumnName="id")
    //@JsonIgnoreProperties(value = "customer")
    private List<Address> billingAddress;

    @ManyToMany(targetEntity=Address.class, cascade=CascadeType.ALL)
    @JoinColumn(name="addressid",referencedColumnName="id")
    //@JsonIgnoreProperties(value = "customer")
    private List<Address> shippingAddress;
   
}

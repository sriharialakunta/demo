package com.wipro.capstone.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.capstone.entity.Customer;
import com.wipro.capstone.service.CustomerService;



@RestController
@RequestMapping("/api/customers")

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getall")
     public ResponseEntity< List<Customer>> getAllCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(
        		customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer>getCustomerById(@PathVariable int id) {
    	return ResponseEntity.status(HttpStatus.OK).body(
             customerService.getCustomerById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {   
        return ResponseEntity.status(HttpStatus.CREATED).body(
        		customerService.createCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customerDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(
        		customerService.updateCustomer(id, customerDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
    
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted Id"+id);
    }
}
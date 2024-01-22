package com.wipro.shopping.feign;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wipro.shopping.model.Customer;

@FeignClient(name = "CUSTOMER", url="${customerService}")
public interface CustomerFeign {

	@GetMapping("/getall")
	public ResponseEntity<List<Customer>> getAllCustomers();

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id);

	@PostMapping("/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer);

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customerDetails);

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable int id);

}
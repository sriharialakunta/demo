package com.wipro.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Customer;
import com.wipro.capstone.exception.MailAlreadyExitsException;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.repository.CustomerRepository;



@Service
public class CustomerService {
	

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with this id : "+ id+" not found"));
    }


    public Customer createCustomer(Customer customer)  {
    	Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
    	if (existingCustomer != null) {
    	throw new MailAlreadyExitsException("Customer with this email already exists.");
    	}
    	return customerRepository.save(customer);
    	}
    	

    public Customer updateCustomer(int id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with this id : "+ id+" not found"));
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with this id : "+ id+" not found"));
         customerRepository.delete(customer);
        
        
    }
        
 
}

package com.wipro.capstone.Service.testing;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wipro.capstone.entity.Address;
import com.wipro.capstone.entity.Customer;
import com.wipro.capstone.repository.CustomerRepository;
import com.wipro.capstone.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CustomerServiceTesting {
	
	@Autowired
	private CustomerService customerservice;

	@MockBean
	private CustomerRepository customerrepo;
	
	@Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("sriveni");
        customer.setEmail("john@example.com");
        customer.setBillingAddress(List.of(new Address(1,"16-16-19","Balaji nagar","guntur","guntur","522001")));
        customer.setShippingAddress(List.of(new Address(2,"16-16-18","Balaji nagar1","guntur","guntur","522001")));
        
        when(customerrepo.save(any(Customer.class))).thenReturn(customer);
       Customer result = customerservice.createCustomer(customer);
       verify(customerrepo).save(customer);
       assertNotNull(result);
       assertEquals(customer,result);
    }
	
	@Test
	
    public void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());
        when(customerrepo.findAll()).thenReturn(customers);
       List<Customer> result = customerservice.getAllCustomers();
        verify(customerrepo).findAll();
        assertNotNull(result);
        assertEquals(customers,result);

    }
	
	@Test
    public void testGetCustomerById() {
        Customer customer = new Customer();
        when(customerrepo.findById(1)).thenReturn(Optional.of(customer));
        Customer result = customerservice.getCustomerById(1);
        verify(customerrepo).findById(1);
        assertNotNull(result);
        assertEquals(customer, result);
    }
	
	@Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        Customer updatedCustomer = new Customer();
        when(customerrepo.findById(1)).thenReturn(Optional.of(customer));
        when(customerrepo.save(customer)).thenReturn(updatedCustomer);
       
    }
	
	@Test
    public void testdeleteCustomer() {
        Customer customer = new Customer();
        // int id = 1;
        when(customerrepo.findById(1)).thenReturn(Optional.of(customer));
        customerrepo.deleteById(1);
        
        
        
	}

}

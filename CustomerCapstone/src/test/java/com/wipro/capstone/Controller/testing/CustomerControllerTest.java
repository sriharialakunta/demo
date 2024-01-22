package com.wipro.capstone.Controller.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.capstone.Controller.CustomerController;
import com.wipro.capstone.entity.Address;
import com.wipro.capstone.entity.Customer;
import com.wipro.capstone.service.CustomerService;

//@RunWith(MockitoJUnitRunner.class)

@WebMvcTest(CustomerController.class)
//@SpringBootTest
public class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerservice;
	private Customer customer;
	final ObjectMapper objectMapper = new ObjectMapper();
	@BeforeEach
	public void init() {
		
	    customer = new Customer();
        customer.setId(1);
        customer.setName("sriveni");
        customer.setEmail("john@example.com");
        customer.setBillingAddress(List.of(new Address(1,"16-16-19","Balaji nagar","guntur","guntur","522001")));
        customer.setShippingAddress(List.of(new Address(1,"16-16-18","Balaji nagar1","guntur","guntur","522001")));
       
		
	}
	@Test
	void testgetallcustomers() throws Exception {
		
		when(customerservice.getAllCustomers()).thenReturn(List.of(customer));
		this.mockMvc.perform(get("/api/customers/getall"))
		.andExpect(status().isOk())
		 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
 .andExpect(jsonPath("$[0].id",is(customer.getId())))
 .andExpect(jsonPath("$[0].name",is(customer.getName())))
 .andExpect(jsonPath("$[0].email",is(customer.getEmail())))
 .andExpect(jsonPath("$[0].billingAddress").isArray())
		.andExpect(jsonPath("$[0].billingAddress[0].addressid").value(customer.getBillingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$[0].billingAddress[0].dno").value("16-16-19"))
		.andExpect(jsonPath("$[0].billingAddress[0].streetname").value("Balaji nagar"))
		.andExpect(jsonPath("$[0].billingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$[0].billingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$[0].billingAddress[0].pincode").value("522001"))
.andExpect(jsonPath("$[0].shippingAddress").isArray())
		.andExpect(jsonPath("$[0].shippingAddress[0].addressid").value(customer.getBillingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$[0].shippingAddress[0].dno").value("16-16-18"))
		.andExpect(jsonPath("$[0].shippingAddress[0].streetname").value("Balaji nagar1"))
		.andExpect(jsonPath("$[0].shippingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$[0].shippingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$[0].shippingAddress[0].pincode").value("522001"));	
		
	}
	
	@Test
	void testCreateCustomer() throws Exception {
		when(customerservice.createCustomer(customer)).thenReturn(customer);
		mockMvc.perform(post("/api/customers/create")
		 .contentType(MediaType.APPLICATION_JSON)
		 .content(objectMapper.writeValueAsString(customer)))
		 .andExpect(status().isCreated())
          
 .andExpect(jsonPath("$.id",is(customer.getId())))
 .andExpect(jsonPath("$.name",is(customer.getName())))
 .andExpect(jsonPath("$.email",is(customer.getEmail())))
 .andExpect(jsonPath("$.billingAddress").isArray())
		.andExpect(jsonPath("$.billingAddress[0].addressid").value(customer.getBillingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$.billingAddress[0].dno").value("16-16-19"))
		.andExpect(jsonPath("$.billingAddress[0].streetname").value("Balaji nagar"))
		.andExpect(jsonPath("$.billingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$.billingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$.billingAddress[0].pincode").value("522001"))
.andExpect(jsonPath("$.shippingAddress").isArray())
		.andExpect(jsonPath("$.shippingAddress[0].addressid").value(customer.getBillingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$.shippingAddress[0].dno").value("16-16-18"))
		.andExpect(jsonPath("$.shippingAddress[0].streetname").value("Balaji nagar1"))
		.andExpect(jsonPath("$.shippingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].pincode").value("522001"));	
		
	}

	@Test
	void testgetCustomerById() throws Exception {
		int id = 1;
		when(customerservice.getCustomerById(id)).thenReturn(customer);
		this.mockMvc.perform(get("/api/customers/"+id))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
 .andExpect(jsonPath("$.id",is(customer.getId())))
 .andExpect(jsonPath("$.name",is(customer.getName())))
 .andExpect(jsonPath("$.email",is(customer.getEmail())))
 .andExpect(jsonPath("$.billingAddress").isArray())
		.andExpect(jsonPath("$.billingAddress[0].addressid").value(customer.getBillingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$.billingAddress[0].dno").value("16-16-19"))
		.andExpect(jsonPath("$.billingAddress[0].streetname").value("Balaji nagar"))
		.andExpect(jsonPath("$.billingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$.billingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$.billingAddress[0].pincode").value("522001"))
.andExpect(jsonPath("$.shippingAddress").isArray())
		.andExpect(jsonPath("$.shippingAddress[0].addressid").value(customer.getBillingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$.shippingAddress[0].dno").value("16-16-18"))
		.andExpect(jsonPath("$.shippingAddress[0].streetname").value("Balaji nagar1"))
		.andExpect(jsonPath("$.shippingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].pincode").value("522001"));	
		
	}
	@Test
	void testdeleteCustomer() throws Exception {
		int id = 1;
		
		this.mockMvc.perform(delete("/api/customers/"+id)
		
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
        .andExpect(content().string("deleted Id"+id));
	}	
	
}





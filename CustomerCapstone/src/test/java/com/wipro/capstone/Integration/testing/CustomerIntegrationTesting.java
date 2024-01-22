package com.wipro.capstone.Integration.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.wipro.capstone.CustomerCapstoneApplication;
import com.wipro.capstone.entity.Address;
import com.wipro.capstone.entity.Customer;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerCapstoneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test2")
class CustomerIntegrationTesting {

	@Autowired
	 private MockMvc mockMvc;
	
	private Customer customer;
	
	@BeforeEach
	public void init() {
		
	    customer = new Customer();
        customer.setId(1);
        customer.setName("sriveni");
        customer.setEmail("john@example.com");
        customer.setBillingAddress(List.of(new Address(1,"16-16-19","Balaji nagar","guntur","guntur","522001")));
        customer.setShippingAddress(List.of(new Address(2,"16-16-18","Balaji nagar1","guntur","guntur","522001")));
       
		
	}
	

	@Test
	@Order(2)
	void testgetallcustomers() throws Exception {
		
//		when(customerservice.getAllCustomers()).thenReturn(List.of(customer));
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
		.andExpect(jsonPath("$[0].shippingAddress[0].addressid").value(customer.getShippingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$[0].shippingAddress[0].dno").value("16-16-18"))
		.andExpect(jsonPath("$[0].shippingAddress[0].streetname").value("Balaji nagar1"))
		.andExpect(jsonPath("$[0].shippingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$[0].shippingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$[0].shippingAddress[0].pincode").value("522001"));	
		
	}
	
	@Test
	@Order(1)
	void testCreateCustomer() throws Exception {

		String json = "{\"name\":\"sriveni\",\"email\":\"john@example.com\",\"billingAddress\":[{\"dno\":\"16-16-19\",\"streetname\":\"Balaji nagar\",\"layout\":\"guntur\",\"city\":\"guntur\",\"pincode\":\"522001\"}],"
				+ "\"shippingAddress\":[{\"dno\":\"16-16-18\",\"streetname\":\"Balaji nagar1\",\"layout\":\"guntur\",\"city\":\"guntur\",\"pincode\":\"522001\"}]}";
		this.mockMvc.perform(post("/api/customers/create")
		 .contentType(MediaType.APPLICATION_JSON)
		 .content(json))
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
		.andExpect(jsonPath("$.shippingAddress[0].addressid").value(customer.getShippingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$.shippingAddress[0].dno").value("16-16-18"))
		.andExpect(jsonPath("$.shippingAddress[0].streetname").value("Balaji nagar1"))
		.andExpect(jsonPath("$.shippingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].pincode").value("522001"));	
		
	}

	@Test
	@Order(3)
	void testgetCustomerById() throws Exception {
		int id = 1;

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
		.andExpect(jsonPath("$.shippingAddress[0].addressid").value(customer.getShippingAddress().get(0).getAddressid()))
		.andExpect(jsonPath("$.shippingAddress[0].dno").value("16-16-18"))
		.andExpect(jsonPath("$.shippingAddress[0].streetname").value("Balaji nagar1"))
		.andExpect(jsonPath("$.shippingAddress[0].layout").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].city").value("guntur"))
		.andExpect(jsonPath("$.shippingAddress[0].pincode").value("522001"));	
		
	}
	@Test
	@Order(4)
	void testdeleteCustomer() throws Exception {
		int id = 1;
		
		this.mockMvc.perform(delete("/api/customers/"+id)
		
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
        .andExpect(content().string("deleted Id"+id));
	}	
	
}

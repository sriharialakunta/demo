package com.wipro.capstone.Integration.testing;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.capstone.CustomerCapstoneApplication;
import com.wipro.capstone.entity.Address;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerCapstoneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class AddressIntegrationTesting {
	
	@Autowired
	private MockMvc mockMvc;

	
	private Address address;

	
	@BeforeEach
	public void init() {
		
	    address = new Address();
	    address.setAddressid(1);
	    address.setDno("16-16-19");
        address.setStreetname("Balaji nagar");
        address.setLayout("Guntur");
        address.setCity("guntur");
        address.setPincode("522001");	
	}
	
	@Test
	@Order(2)
	void testgetalladdress() throws Exception {
		
		
		this.mockMvc.perform(get("/api/customers/address/getall"))
		.andExpect(status().isOk())
		 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
 .andExpect(jsonPath("$[0].addressid",is(address.getAddressid())))
 .andExpect(jsonPath("$[0].dno",is(address.getDno())))
 .andExpect(jsonPath("$[0].streetname",is(address.getStreetname())))
 .andExpect(jsonPath("$[0].layout",is(address.getLayout())))
 .andExpect(jsonPath("$[0].city",is(address.getCity())))
 .andExpect(jsonPath("$[0].pincode",is(address.getPincode())));
 
	}
	@Test
	@Order(1)
	void testCreateAddress() throws Exception {
		
		mockMvc.perform(post("/api/customers/address/create")
		 .contentType(MediaType.APPLICATION_JSON)
		 .content(new ObjectMapper().writeValueAsString(address)))
		 .andExpect(status().isCreated())
          
 .andExpect(jsonPath("$.addressid",is(address.getAddressid())))
 .andExpect(jsonPath("$.dno",is(address.getDno())))
 .andExpect(jsonPath("$.streetname",is(address.getStreetname())))
 .andExpect(jsonPath("$.layout",is(address.getLayout())))
 .andExpect(jsonPath("$.city",is(address.getCity())))
 .andExpect(jsonPath("$.pincode",is(address.getPincode())));
 
 
	}
	@Test
	@Order(3)
	void testgetAddressById() throws Exception {
		int id = 1;
		
		this.mockMvc.perform(get("/api/customers/address/"+id))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
 .andExpect(jsonPath("$.addressid",is(address.getAddressid())))
 .andExpect(jsonPath("$.dno",is(address.getDno())))
 .andExpect(jsonPath("$.streetname",is(address.getStreetname())))
 .andExpect(jsonPath("$.layout",is(address.getLayout())))
 .andExpect(jsonPath("$.city",is(address.getCity())))
 .andExpect(jsonPath("$.pincode",is(address.getPincode())));
 
		
	}
	@Test
	@Order(4)
	void testdeleteAddress() throws Exception {
		int id = 1;
		
		this.mockMvc.perform(delete("/api/customers/address/"+id)
		
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
        .andExpect(content().string("deleted Id"+id));
	}	
	

}

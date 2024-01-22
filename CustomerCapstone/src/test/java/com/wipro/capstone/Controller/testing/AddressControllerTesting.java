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
import com.wipro.capstone.Controller.AddressController;
import com.wipro.capstone.entity.Address;
import com.wipro.capstone.service.AddressService;

@WebMvcTest(AddressController.class)
class AddressControllerTesting {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddressService addressservice;
	private Address address;
	final ObjectMapper objectMapper = new ObjectMapper();
	
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
	void testgetalladdress() throws Exception {
		
		when(addressservice.getAllAddress()).thenReturn(List.of(address));
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
	void testCreateAddress() throws Exception {
		when(addressservice.createAddress(address)).thenReturn(address);
		mockMvc.perform(post("/api/customers/address/create")
		 .contentType(MediaType.APPLICATION_JSON)
		 .content(objectMapper.writeValueAsString(address)))
		 .andExpect(status().isCreated())
          
 .andExpect(jsonPath("$.addressid",is(address.getAddressid())))
 .andExpect(jsonPath("$.dno",is(address.getDno())))
 .andExpect(jsonPath("$.streetname",is(address.getStreetname())))
 .andExpect(jsonPath("$.layout",is(address.getLayout())))
 .andExpect(jsonPath("$.city",is(address.getCity())))
 .andExpect(jsonPath("$.pincode",is(address.getPincode())));
 
 
	}
	@Test
	void testgetAddressById() throws Exception {
		int id = 1;
		when(addressservice.getAddressById(id)).thenReturn(address);
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
	void testdeleteAddress() throws Exception {
		int id = 1;
		
		this.mockMvc.perform(delete("/api/customers/address/"+id)
		
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
        .andExpect(content().string("deleted Id"+id));
	}	
	

	

	

}

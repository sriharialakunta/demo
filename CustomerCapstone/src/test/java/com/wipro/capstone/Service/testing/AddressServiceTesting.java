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
import com.wipro.capstone.repository.AddressRepository;
import com.wipro.capstone.service.AddressService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AddressServiceTesting {

	@Autowired
	private AddressService addressservice;

	@MockBean
	private AddressRepository addressrepository;
	
	@Test
	public void testCreateAddress() {
		Address add = new Address();
		add.setAddressid(1);
		add.setCity("sriveni");
		add.setDno("john@example.com");
		add.setLayout("guntur");
		add.setPincode("guntur");
		add.setStreetname("guntur");
        when(addressrepository.save(any(Address.class))).thenReturn(add);
       Address result = addressservice.createAddress(add);
       verify(addressrepository).save(add);
       assertNotNull(result);
       assertEquals(add,result);
    }
@Test
	
    public void getAllAddress() {
        List<Address> address = new ArrayList<>();
        address.add(new Address());
        address.add(new Address());
        when(addressrepository.findAll()).thenReturn(address);
       List<Address> result = addressservice.getAllAddress();
        verify(addressrepository).findAll();
        assertNotNull(result);
        assertEquals(address,result);

    }
@Test
public void testGetAddressById() {
    Address address = new Address();
    when(addressrepository.findById(1)).thenReturn(Optional.of(address));
    Address result = addressservice.getAddressById(1);
    verify(addressrepository).findById(1);
    assertNotNull(result);
    assertEquals(address, result);
}

@Test
public void testUpdateAddress() {
    Address address = new Address();
    Address updatedaddress = new Address();
    when(addressrepository.findById(1)).thenReturn(Optional.of(address));
    when(addressrepository.save(address)).thenReturn(updatedaddress);
   
}

@Test
public void testdeleteAddress() {
	Address address = new Address();
    // int id = 1;
    when(addressrepository.findById(1)).thenReturn(Optional.of(address));
    addressrepository.deleteById(1);
    
    
    
}

   
 	}

  

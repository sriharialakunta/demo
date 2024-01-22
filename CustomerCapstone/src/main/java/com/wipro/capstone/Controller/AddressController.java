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

import com.wipro.capstone.entity.Address;
import com.wipro.capstone.service.AddressService;

@RestController
@RequestMapping("/api/customers/address")

public class AddressController {

	 @Autowired
	    private AddressService service;

	    @GetMapping("/getall")
	    public ResponseEntity<List<Address>> getAllAddress() {
	        return ResponseEntity.status(HttpStatus.OK).body(
	        		service.getAllAddress());
	    }
        /**
         * 
         * @param id
         * @return
         */
        
	    @GetMapping("/{id}")
	    public ResponseEntity<Address> getAddressById(@PathVariable int id) {
	        return ResponseEntity.status(HttpStatus.OK).body(
	        		service.getAddressById(id));
	    }

	    @PostMapping("/create")
	    public ResponseEntity<Address> createAddress(@RequestBody Address customer) {
	        return ResponseEntity.status(HttpStatus.CREATED).body(
	        		service.createAddress(customer));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Address> updateAddress(@PathVariable int id, @RequestBody Address customerDetails) {
	        return ResponseEntity.status(HttpStatus.OK).body(
	        		service.updateAddress(id, customerDetails));
	    }

	    @DeleteMapping("/{id}")
	    public  ResponseEntity<String> deleteAddress(@PathVariable int id) {
	        service.deleteAddress(id);
	        return ResponseEntity.status(HttpStatus.OK).body("deleted Id"+id);
	    }
	
}

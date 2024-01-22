package com.wipro.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.capstone.entity.Address;
import com.wipro.capstone.exception.ResourceNotFoundException;
import com.wipro.capstone.repository.AddressRepository;


@Service
public class AddressService {

	@Autowired
    private AddressRepository customeraddRepository;

    public List<Address> getAllAddress() {
        return customeraddRepository.findAll();
    }

    public Address getAddressById(int addressid) {
        return customeraddRepository.findById(addressid).orElseThrow(() -> new ResourceNotFoundException("Addressid" + addressid));
    }

    public Address createAddress(Address add) {
        return customeraddRepository.save(add);
    }

    public Address updateAddress(int addressid, Address customerDetails) {
        Address add = customeraddRepository.findById(addressid).orElseThrow(() -> new ResourceNotFoundException("Address with this id : "+ addressid+" not found"));
        add.setAddressid(customerDetails.getAddressid());
        add.setDno(customerDetails.getDno());
        add.setLayout(customerDetails.getLayout());
        add.setStreetname(customerDetails.getStreetname());
        add.setCity(customerDetails.getCity());
        add.setPincode(customerDetails.getPincode());
        return customeraddRepository.save(add);
    }

    public void deleteAddress(int addressid) {
        Address add = customeraddRepository.findById(addressid).orElseThrow(() -> new ResourceNotFoundException("Address with this id : "+ addressid+" not found"));
        customeraddRepository.delete(add);
    }
        
	
}

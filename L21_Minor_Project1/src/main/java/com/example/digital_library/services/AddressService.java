package com.example.digital_library.services;

import com.example.digital_library.models.Address;
import com.example.digital_library.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address addAddress(Address address){
        return this.addressRepository.save(address);
    }
}

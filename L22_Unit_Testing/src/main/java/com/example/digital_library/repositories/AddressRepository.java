package com.example.digital_library.repositories;

import com.example.digital_library.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}

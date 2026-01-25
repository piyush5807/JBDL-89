package com.example.digital_library.services;

import com.example.digital_library.models.Admin;
import com.example.digital_library.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public void addAdmin(String name, String email){

        Admin admin = Admin.builder().name(name).email(email).build();
        this.adminRepository.save(admin);

    }
}

package com.example.digital_library.services;

import com.example.digital_library.dtos.AdminSignupRequestDTO;
import com.example.digital_library.dtos.SignupRequestDTO;
import com.example.digital_library.models.Admin;
import com.example.digital_library.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Admin addAdmin(SignupRequestDTO signupRequestDTO) {

        Admin admin = Admin
                .builder()
                .name(signupRequestDTO.getName())
                .email(signupRequestDTO.getEmail())
                .build();
        return this.adminRepository.save(admin);

    }

    public Admin findById(int id) {
        return this.adminRepository.findById(id).orElse(null);
    }


}

package com.example.digital_library.controllers;

import com.example.digital_library.models.Admin;
import com.example.digital_library.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/admins")
    public void createAdmin(@RequestParam("name") String name,
                            @RequestParam("email") String email) {
        this.adminService.addAdmin(name, email);

    }
}

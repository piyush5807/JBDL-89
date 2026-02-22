package com.example.digital_library.controllers;

import com.example.digital_library.models.Admin;
import com.example.digital_library.models.Student;
import com.example.digital_library.models.User;
import com.example.digital_library.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")     // can be invoked by only student
    public Admin getAdmin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();


        Integer adminId = user.getAdmin().getId();
        return this.adminService.findById(adminId);
    }
}

package com.example.digital_library.controllers;

import com.example.digital_library.dtos.AdminSignupRequestDTO;
import com.example.digital_library.dtos.StudentSignupRequestDTO;
import com.example.digital_library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/student/signup")
    public void signupStudent(@RequestBody StudentSignupRequestDTO studentSignupRequestDTO){
        this.userService.createUser(studentSignupRequestDTO);
    }

    // Admin sign up - it will be permit all as anyone will try to become admin for this platform
    // We will create an admin in the beginning when the application starts and then that admin can add other admins

    @PostMapping("/admin/signup")
    public void signupStudent(@RequestBody AdminSignupRequestDTO adminSignupRequestDTO){
        this.userService.createUser(adminSignupRequestDTO);
    }
}

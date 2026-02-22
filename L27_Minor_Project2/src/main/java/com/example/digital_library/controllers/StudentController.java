package com.example.digital_library.controllers;

import com.example.digital_library.models.Student;
import com.example.digital_library.models.User;
import com.example.digital_library.services.StudentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/student")     // can be invoked by only student
    public Student getStudent(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();


        Integer studentId = user.getStudent().getId();
        return this.studentService.findById(studentId);
    }

    @GetMapping("/student-by-id")  // invoked by an admin
    public Student getStudentById(@RequestParam("id") Integer id){
        return this.studentService.findById(id);
    }
}

package com.example.digital_library.controllers;

import com.example.digital_library.dtos.CreateStudentRequest;
import com.example.digital_library.models.Student;
import com.example.digital_library.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/students")
    public void addStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
        this.studentService.add(createStudentRequest);
    }
}

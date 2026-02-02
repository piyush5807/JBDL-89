package com.example.digital_library.services;

import com.example.digital_library.dtos.CreateStudentRequest;
import com.example.digital_library.models.Address;
import com.example.digital_library.models.Student;
import com.example.digital_library.repositories.AddressRepository;
import com.example.digital_library.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressService addressService;

    public void add(CreateStudentRequest createStudentRequest){

        Student student = createStudentRequest.to();

        /**
         First approach : 3 db calls

        // Step 1: Adding the student without address
        // Step 2: add the address in the address table
        // Step 3: Update the address in the student table

         */

        /**
         * Second approach : 2 db calls
         *
         * Step 1: add the address in the address table
         * Step 2: Add the student along with the address id created in step1 in the student table
         */

        this.addressService.addAddress(student.getAddress());
        this.studentRepository.save(student);

    }

    /**
     * 1. foreign key column can hold null values
     * 2. can foreign key column hold some value which is not present in the parent table ? No
     */

    public Student findById(Integer studentId){
        return this.studentRepository.findById(studentId).orElse(null);
    }
}

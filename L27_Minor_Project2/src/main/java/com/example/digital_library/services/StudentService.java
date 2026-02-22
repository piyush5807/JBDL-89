package com.example.digital_library.services;

import com.example.digital_library.dtos.SignupRequestDTO;
import com.example.digital_library.dtos.StudentSignupRequestDTO;
import com.example.digital_library.models.Student;
import com.example.digital_library.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressService addressService;

    public Student add(SignupRequestDTO signupRequestDTO){

        StudentSignupRequestDTO createStudentRequest = (StudentSignupRequestDTO) signupRequestDTO ;
        Student student = createStudentRequest.toStudent();

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

        if(student.getAddress() != null) {
            this.addressService.addAddress(student.getAddress());
        }
        return this.studentRepository.save(student);

    }

    /**
     * 1. foreign key column can hold null values
     * 2. can foreign key column hold some value which is not present in the parent table ? No
     */

    public Student findById(Integer studentId){
        // TODO: Fetch the details from cache and then if not found go to the mysql db
        return this.studentRepository.findById(studentId).orElse(null);
    }
}

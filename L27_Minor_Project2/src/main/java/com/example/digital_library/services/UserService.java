package com.example.digital_library.services;

import com.example.digital_library.dtos.SignupRequestDTO;
import com.example.digital_library.dtos.StudentSignupRequestDTO;
import com.example.digital_library.models.Admin;
import com.example.digital_library.models.Student;
import com.example.digital_library.models.User;
import com.example.digital_library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    public void createUser(SignupRequestDTO signupRequestDTO) {

        User user = signupRequestDTO.toUser();
        String encodedPwd = this.passwordEncoder.encode(signupRequestDTO.getPassword());
        user.setPassword(encodedPwd);

        if(signupRequestDTO instanceof StudentSignupRequestDTO){
            Student student = this.studentService.add(signupRequestDTO);
            user.setAuthorities("STUDENT");
            user.setStudent(student);
        } else {

            Admin admin = this.adminService.addAdmin(signupRequestDTO);
            user.setAuthorities("ADMIN");
            user.setAdmin(admin);

        }

        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }
}

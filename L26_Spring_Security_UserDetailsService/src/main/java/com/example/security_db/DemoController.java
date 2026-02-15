package com.example.security_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @GetMapping("/student")
    public String student() {
        return "Hello Student!!";
    }

    @GetMapping("/faculty")
    public String faculty() {
        return "Hello Faculty!!";
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World!!";
    }

    @PostMapping("/student/signup")
    public User signupStudent(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        User user = User.builder().username(signUpRequestDTO.getUsername())
                .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
                .authorities("STUDENT")
                .name(signUpRequestDTO.getName())
                .build();

        return this.userService.addUser(user);
    }

    @PostMapping("/faculty/signup")
    public User signupFaculty(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        User user = User.builder().username(signUpRequestDTO.getUsername())
                .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
                .name(signUpRequestDTO.getName())
                .authorities("FACULTY")
                .build();

        return this.userService.addUser(user);
    }

}

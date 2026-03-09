package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    public User createUser(CreateUserRequestDTO createUserRequestDTO) {

        try {

            User user = createUserRequestDTO.to();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthorities("USER");

            user = this.userRepository.save(user); // throws an error

            // TODO: Publish a kafka event on user creation - user_Created
            // user_creation_push_failed - DLQ


            return user;
        } catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }
}

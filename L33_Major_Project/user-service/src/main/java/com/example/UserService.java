package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserService implements UserDetailsService {

    private static String USER_CREATED_TOPIC = "user-created";

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    public AbstractUser createUser(CreateUserRequestDTO createUserRequestDTO) {

            AbstractUser user = createUserRequestDTO.to();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthorities("USER");

            user = this.userRepository.save(user); // throws an error

            String data = this.objectMapper.writeValueAsString(user);

//             TODO - complete: Publish a kafka event on user creation - user_created
            kafkaTemplate.send(USER_CREATED_TOPIC, data);

            return user;
    }
}

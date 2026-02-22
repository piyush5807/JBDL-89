package com.example.digital_library;

import com.example.digital_library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(HttpMethod.POST, "/student/signup").permitAll()
                                        .requestMatchers("/student-by-id/**").hasAuthority("ADMIN")
                                        .requestMatchers("/student/**").hasAuthority("STUDENT")
                                        .requestMatchers(HttpMethod.GET, "/books/**").hasAnyAuthority("STUDENT", "ADMIN")
                                        .requestMatchers( "/books/**").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                        .requestMatchers("/transaction/initiate/**").hasAuthority("STUDENT")
                                        .requestMatchers("/transaction/**").hasAnyAuthority("STUDENT", "ADMIN")
                                        .anyRequest().permitAll()

                ).httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}

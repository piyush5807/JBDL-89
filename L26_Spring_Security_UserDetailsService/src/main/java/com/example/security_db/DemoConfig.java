package com.example.security_db;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoConfig {

    // This is only for in memory authentication
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withUsername("Amit")
//                .password("amit@123")
////                .roles("STUDENT")
//                .authorities("STUDENT")
//                .build();
//
//        UserDetails user2 = User.withUsername("Abhinay")
//                .password("abhinay@123")
////                .roles("FACULTY")
//                .authorities("FACULTY")
//                .build();
//
//        UserDetails user3 = User.withUsername("Sakshi")
//                .password("sakshi@123")
//                .authorities("STUDENT", "FACULTY")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2, user3);
//    }

    @Autowired
    UserService userService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder2());

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
                                .requestMatchers(HttpMethod.POST, "/student/**").permitAll()
                                .requestMatchers("/student/**").hasAuthority("STUDENT")
                                .requestMatchers(HttpMethod.POST, "/faculty/**").permitAll()
                                .requestMatchers("/faculty/**").hasAuthority("FACULTY")
                                .anyRequest().permitAll()

        ).httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        // CSRF token validation should be present in your application if there are some unsafe unauthenticated / open apis


        return http.build();
    }

    // This is very unsecure password encoder, we should not use this in production envs, instead use a stronger encoder
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }
}

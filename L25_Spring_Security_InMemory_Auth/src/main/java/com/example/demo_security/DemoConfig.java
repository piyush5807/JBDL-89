//package com.example.demo_security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class DemoConfig{
//
//    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails user = User.builder()
//                .username("anuj123@gmail.com")
//                .password("anuj@geeks")
//                .roles("STUDENT")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("anuradha123@yahoo.co.in")
//                .password("anu@geeks")
//                .roles("FACULTY")
//                .build();
//
//        UserDetails user3 = User.builder()
//                .username("argha@hotmail.com")
//                .password("argha@geeks")
//                .roles("FACULTY", "STUDENT")
//                .build();
//
//        return new InMemoryUserDetailsManager(user,user2,user3);
//    }
//
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        return http.authorizeHttpRequests(
//                auth ->
//                        auth
//                                .requestMatchers("/student/**").hasRole("STUDENT")
//                                .requestMatchers("/faculty/**").hasRole("FACULTY")
//                                .anyRequest().permitAll()
//                                 // bypassing the filter chain, no authentication no access control check
//
//        ).formLogin(form -> form
//                .loginPage("/login")
//                .permitAll()
//        ).build();
//    }
//
//    @Bean
//    PasswordEncoder getPE(){
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//
//}

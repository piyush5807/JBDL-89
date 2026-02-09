package com.example.demo_security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DemoConfigOld extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("anuj@gmail.com")
                .password("anuj@123")
                .roles("STUDENT")
                .and()
                .withUser("anuradha@yahoo.co.in")
                .password("anuradha@123")
                .roles("FACULTY")
                .and()
                .withUser("amit@hotmail.com")
                .password("amit@123")
                .roles("FACULTY", "STUDENT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/student/**").hasRole("STUDENT")
                .antMatchers("/faculty/**").hasRole("FACULTY")
                .anyRequest().permitAll()
                .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}


